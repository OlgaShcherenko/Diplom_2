package ru.praktikum.diplom_2.clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.praktikum.diplom_2.pojo.CreateUserRequest;
import ru.praktikum.diplom_2.pojo.LoginUserRequest;
import ru.praktikum.diplom_2.pojo.ChangeUserDataRequest;

import static io.restassured.RestAssured.given;

public class UserClient extends BaseClient {
    public static final String CREATE_USER_PATH = "/api/auth/register";
    public static final String LOGIN_USER_PATH = "/api/auth/login";
    public static final String DATA_USER_PATH = "/api/auth/user";

    @Step("User creation request")
    public ValidatableResponse createUser(CreateUserRequest createUserRequest) {
        return given()
                .spec(getSpec())
                .body(createUserRequest)
                .when()
                .post(CREATE_USER_PATH)
                .then();
    }

    @Step("User authorization request")
    public ValidatableResponse loginUser(LoginUserRequest loginUserRequest) {
        return given()
                .spec(getSpec())
                .body(loginUserRequest)
                .when()
                .post(LOGIN_USER_PATH)
                .then();
    }

    @Step("Request to change user data")
    public ValidatableResponse changeDataUser(String accessToken, ChangeUserDataRequest changeUserDataRequest) {
        return given()
                .header("authorization", accessToken)
                .spec(getSpec())
                .body(changeUserDataRequest)
                .when()
                .patch(DATA_USER_PATH)
                .then();
    }

    @Step("User delete request")
    public void deleteUser(String accessToken) {
        given()
                .header("authorization", accessToken)
                .spec(getSpec())
                .when()
                .delete(DATA_USER_PATH);
    }
}