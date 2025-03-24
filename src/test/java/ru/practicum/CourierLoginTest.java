package ru.practicum;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.client.CourierClient;
import ru.practicum.model.Courier;
import ru.practicum.model.CourierResponse;
import ru.practicum.model.ErrorResponse;
import ru.practicum.util.DataGenerator;

import static org.junit.Assert.*;

@Epic("Курьеры")
@Feature("Логин курьера")
public class CourierLoginTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = DataGenerator.getRandomCourier();

        // Создаем курьера перед тестами логина
        courierClient.create(courier)
                .then().statusCode(201);

        // Получаем ID созданного курьера сразу после создания
        CourierResponse createdCourier = courierClient.login(courier)
                .then().statusCode(200)
                .extract().as(CourierResponse.class);

        courierId = createdCourier.getId();
    }

    @After
    public void tearDown() {
        if (courierId > 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Курьер может авторизоваться и получить свой ID")
    @Description("Проверка, что курьер может авторизоваться с правильными логином и паролем и получает свой ID")
    public void courierCanLoginAndReturnId() {
        CourierResponse response = courierClient.login(courier)
                .then().statusCode(200)
                .extract().as(CourierResponse.class);

        assertNotNull(response.getId());
        assertTrue(response.getId() > 0);
        assertEquals((long) courierId, (long) response.getId());
    }

    @Test
    @DisplayName("Для авторизации нужен и логин, и пароль")
    @Description("Проверка, что нельзя авторизоваться без логина или пароля")
    public void loginRequiresLoginAndPasswordField() {
        Courier loginRequest = new Courier(null, courier.getPassword());

        ErrorResponse response = courierClient.login(loginRequest)
                .then().statusCode(400)
                .extract().as(ErrorResponse.class);

        assertEquals("Недостаточно данных для входа", response.getMessage());

        loginRequest = new Courier(courier.getLogin(), "");

        response = courierClient.login(loginRequest)
                .then().statusCode(400)
                .extract().as(ErrorResponse.class);

        assertEquals("Недостаточно данных для входа", response.getMessage());
    }

    @Test
    @DisplayName("Нельзя авторизоваться с неверным логином или неверным паролем")
    @Description("Проверка, что система вернет ошибку при попытке авторизации с неверным логином или паролем")
    public void cannotLoginWithWrongCredentials() {
        Courier loginRequest = new Courier("неверный_логин", courier.getPassword());

        ErrorResponse response = courierClient.login(loginRequest)
                .then().statusCode(404)
                .extract().as(ErrorResponse.class);

        assertEquals("Учетная запись не найдена", response.getMessage());

        loginRequest = new Courier(courier.getLogin(), "неверный_пароль");

        response = courierClient.login(loginRequest)
                .then().statusCode(404)
                .extract().as(ErrorResponse.class);

        assertEquals("Учетная запись не найдена", response.getMessage());
    }
}