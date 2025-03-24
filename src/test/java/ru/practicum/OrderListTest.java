package ru.practicum;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.client.OrderClient;
import ru.practicum.model.OrderResponse;

import static org.junit.Assert.*;

@Epic("Заказы")
@Feature("Получение списка заказов")
public class OrderListTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка, что в ответе возвращается список заказов")
    public void canGetOrdersList() {
        OrderResponse response = orderClient.getOrders()
                .then().statusCode(200)
                .extract().as(OrderResponse.class);

        assertNotNull(response.getOrders());
        assertFalse(response.getOrders().isEmpty());
    }
}