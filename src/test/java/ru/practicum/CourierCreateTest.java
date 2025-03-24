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
@Feature("Создание курьера")
public class CourierCreateTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = DataGenerator.getRandomCourier();
        courierId = 0;
    }

    @After
    public void tearDown() {
        if (courierId > 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Создание курьера с корректными данными")
    @Description("Проверка, что курьера можно создать с корректными данными и получить ответ с ok:true")
    public void canCreateCourierWithValidData() {
        CourierResponse response = courierClient.create(courier)
                .then().statusCode(201)
                .extract().as(CourierResponse.class);

        assertTrue(response.isOk());

        // Получаем ID созданного курьера для удаления в tearDown
        CourierResponse loginResponse = courierClient.login(courier)
                .then().statusCode(200)
                .extract().as(CourierResponse.class);

        courierId = loginResponse.getId();
    }

    @Test
    @DisplayName("Нельзя создать курьера без логина")
    @Description("Проверка, что нельзя создать курьера без логина")
    public void cannotCreateCourierWithoutLogin() {
        courier.setLogin(null);

        ErrorResponse response = courierClient.create(courier)
                .then().statusCode(400)
                .extract().as(ErrorResponse.class);

        assertEquals("Недостаточно данных для создания учетной записи", response.getMessage());
    }

    @Test
    @DisplayName("Нельзя создать курьера без пароля")
    @Description("Проверка, что нельзя создать курьера без пароля")
    public void cannotCreateCourierWithoutPassword() {
        courier.setPassword(null);

        ErrorResponse response = courierClient.create(courier)
                .then().statusCode(400)
                .extract().as(ErrorResponse.class);

        assertEquals("Недостаточно данных для создания учетной записи", response.getMessage());
    }

    @Test
    @DisplayName("Создание курьера без имени")
    @Description("Проверка, что система позволяет создать курьера без имени")
    public void canCreateCourierWithoutFirstName() {
        courier.setFirstName(null);

        // API позволяет создать курьера без firstName (возвращает 201)
        CourierResponse response = courierClient.create(courier)
                .then().statusCode(201)
                .extract().as(CourierResponse.class);

        assertTrue(response.isOk());

        // Получаем ID созданного курьера для удаления в tearDown
        CourierResponse loginResponse = courierClient.login(courier)
                .then().statusCode(200)
                .extract().as(CourierResponse.class);

        courierId = loginResponse.getId();
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("Проверка, что нельзя создать двух курьеров с одинаковым логином")
    public void cannotCreateTwoIdenticalCouriers() {
        // Создаем первого курьера
        courierClient.create(courier)
                .then().statusCode(201);

        // Получаем ID созданного курьера для удаления в tearDown
        CourierResponse loginResponse = courierClient.login(courier)
                .then().statusCode(200)
                .extract().as(CourierResponse.class);

        courierId = loginResponse.getId();

        // Пытаемся создать второго курьера с тем же логином
        Courier duplicateCourier = new Courier(courier.getLogin(), "другой_пароль", "другое_имя");

        ErrorResponse response = courierClient.create(duplicateCourier)
                .then().statusCode(409)
                .extract().as(ErrorResponse.class);

        assertTrue(response.getMessage().contains("уже используется"));
    }
}