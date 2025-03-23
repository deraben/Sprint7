package ru.practicum;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.practicum.client.OrderClient;
import ru.practicum.model.Order;
import ru.practicum.model.OrderResponse;
import ru.practicum.util.DataGenerator;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertTrue;

@Epic("Заказы")
@Feature("Создание заказа")
@RunWith(Parameterized.class)
public class OrderCreateTest {
    private OrderClient orderClient;
    private final List<String> colors;

    public OrderCreateTest(List<String> colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {List.of("BLACK")},                // Только черный цвет
                {List.of("GREY")},                 // Только серый цвет
                {List.of("BLACK", "GREY")},        // Оба цвета
                {List.of()}                        // Без цвета
        });
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Создание заказа с разными вариантами цветов")
    @Description("Проверка, что заказ можно создать с различными комбинациями цветов: BLACK, GREY, оба цвета или без цвета")
    public void canCreateOrderWithDifferentColors() {
        Order order = DataGenerator.getRandomOrderWithColor(colors);

        OrderResponse response = orderClient.create(order)
                .then().statusCode(201)
                .extract().as(OrderResponse.class);

        assertTrue(response.getTrack() > 0);
    }
}