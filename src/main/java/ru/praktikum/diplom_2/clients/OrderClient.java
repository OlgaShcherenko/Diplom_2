package ru.praktikum.diplom_2.clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.praktikum.diplom_2.pojo.CreateOrderRequest;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {
    public static final String ORDER_PATH = "/api/orders";

    @Step("Request to create order")
    public ValidatableResponse createOrder(String accessToken, CreateOrderRequest createOrderRequest) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(createOrderRequest)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Get orders from specific user")
    public ValidatableResponse getUsersOrders(String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .get(ORDER_PATH)
                .then();
    }
}