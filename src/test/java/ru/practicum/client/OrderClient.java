package ru.practicum.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.practicum.model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {
    private static final String ORDERS_PATH = "/api/v1/orders";

    @Step("Создание заказа")
    public Response create(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDERS_PATH);
    }

    @Step("Получение списка заказов")
    public Response getOrders() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDERS_PATH);
    }
}