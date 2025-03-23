package ru.practicum.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.practicum.model.Courier;

import static io.restassured.RestAssured.given;

public class CourierClient extends BaseClient {
    private static final String COURIER_PATH = "/api/v1/courier";
    private static final String LOGIN_PATH = "/api/v1/courier/login";

    @Step("Создание курьера {courier.login}")
    public Response create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH);
    }

    @Step("Логин курьера {courier.login}")
    public Response login(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(LOGIN_PATH);
    }

    @Step("Удаление курьера с id {courierId}")
    public Response delete(int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + "/" + courierId);
    }
}