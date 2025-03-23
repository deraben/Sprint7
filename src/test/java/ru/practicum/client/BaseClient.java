package ru.practicum.client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseClient {
    protected static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    protected RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.ALL)
                .addFilter(new AllureRestAssured())
                .build();
    }
}