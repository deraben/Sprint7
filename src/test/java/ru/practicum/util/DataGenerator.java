package ru.practicum.util;

import ru.practicum.model.Courier;
import ru.practicum.model.Order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class DataGenerator {
    private static final Random random = new Random();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Генерация рандомного курьера
    public static Courier getRandomCourier() {
        String login = "courier_" + UUID.randomUUID().toString().substring(0, 8);
        String password = "password_" + UUID.randomUUID().toString().substring(0, 8);
        String firstName = "Курьер_" + UUID.randomUUID().toString().substring(0, 5);

        return new Courier(login, password, firstName);
    }

    // Генерация рандомного заказа
    public static Order getRandomOrder() {
        return new Order(
                "Имя_" + UUID.randomUUID().toString().substring(0, 5),
                "Фамилия_" + UUID.randomUUID().toString().substring(0, 5),
                "Адрес_" + UUID.randomUUID().toString().substring(0, 10),
                String.valueOf(random.nextInt(30) + 1), // Случайный номер метро от 1 до 30
                "+7" + (9000000000L + random.nextInt(1000000000)),
                random.nextInt(10) + 1, // Случайное количество дней аренды от 1 до 10
                LocalDate.now().plusDays(random.nextInt(10)).format(dateFormatter),
                "Комментарий_" + UUID.randomUUID().toString().substring(0, 10),
                List.of() // Пустой список цветов по умолчанию
        );
    }

    // Генерация рандомного заказа с указанными цветами
    public static Order getRandomOrderWithColor(List<String> colors) {
        Order order = getRandomOrder();
        order.setColor(colors);
        return order;
    }
}