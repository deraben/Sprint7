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

        courierId = 0;
    }

    @After
    public void tearDown() {
        if (courierId > 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    @Description("Проверка, что курьер может авторизоваться с правильными логином и паролем")
    public void courierCanLogin() {
        CourierResponse response = courierClient.login(courier)
                .then().statusCode(200)
                .extract().as(CourierResponse.class);

        courierId = response.getId();

        assertTrue(response.getId() > 0);
    }

    @Test
    @DisplayName("Для авторизации нужен логин")
    @Description("Проверка, что нельзя авторизоваться без логина")
    public void loginRequiresLoginField() {
        Courier loginRequest = new Courier(null, courier.getPassword());

        ErrorResponse response = courierClient.login(loginRequest)
                .then().statusCode(400)
                .extract().as(ErrorResponse.class);

        assertEquals("Недостаточно данных для входа", response.getMessage());
    }

    @Test
    @DisplayName("Для авторизации нужен пароль")
    @Description("Проверка, что нельзя авторизоваться без пароля")
    public void loginRequiresPasswordField() {
        // Вместо null используем пустую строку, чтобы JSON был валидным
        Courier loginRequest = new Courier(courier.getLogin(), "");

        ErrorResponse response = courierClient.login(loginRequest)
                .then().statusCode(400)
                .extract().as(ErrorResponse.class);

        assertEquals("Недостаточно данных для входа", response.getMessage());
    }

    @Test
    @DisplayName("Нельзя авторизоваться с неверным логином")
    @Description("Проверка, что система вернет ошибку при попытке авторизации с неверным логином")
    public void cannotLoginWithWrongCredentials() {
        Courier loginRequest = new Courier("неверный_логин", courier.getPassword());

        ErrorResponse response = courierClient.login(loginRequest)
                .then().statusCode(404)
                .extract().as(ErrorResponse.class);

        assertEquals("Учетная запись не найдена", response.getMessage());
    }

    @Test
    @DisplayName("Нельзя авторизоваться с неверным паролем")
    @Description("Проверка, что система вернет ошибку при попытке авторизации с неверным паролем")
    public void cannotLoginWithWrongPassword() {
        Courier loginRequest = new Courier(courier.getLogin(), "неверный_пароль");

        ErrorResponse response = courierClient.login(loginRequest)
                .then().statusCode(404)
                .extract().as(ErrorResponse.class);

        assertEquals("Учетная запись не найдена", response.getMessage());
    }

    @Test
    @DisplayName("Успешный логин возвращает ID курьера")
    @Description("Проверка, что успешный запрос логина возвращает ID курьера")
    public void successfulLoginReturnsId() {
        CourierResponse response = courierClient.login(courier)
                .then().statusCode(200)
                .extract().as(CourierResponse.class);

        courierId = response.getId();

        assertNotNull(response.getId());
        assertTrue(response.getId() > 0);
    }
}