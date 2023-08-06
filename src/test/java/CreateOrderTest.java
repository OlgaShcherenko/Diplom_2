import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.diplom_2.clients.OrderClient;
import ru.praktikum.diplom_2.clients.UserClient;
import ru.praktikum.diplom_2.dataprovider.UserProvider;
import ru.praktikum.diplom_2.pojo.CreateOrderRequest;
import ru.praktikum.diplom_2.pojo.CreateUserRequest;

import java.util.Arrays;
import java.util.List;

public class CreateOrderTest {
    private OrderClient orderClient = new OrderClient();
    private UserClient userClient = new UserClient();
    private String accessToken;
    private List<String> validIngredients = Arrays.asList(
            "61c0c5a71d1f82001bdaaa6d",
            "61c0c5a71d1f82001bdaaa71",
            "61c0c5a71d1f82001bdaaa6e",
            "61c0c5a71d1f82001bdaaa6c");

    private List<String> invalidIngredients = Arrays.asList(
            "123456789",
            "987654321",
            "1122334455");

    @Before
    @DisplayName("Create user")
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        CreateUserRequest createUserRequest = UserProvider.getRandomCreateUserRequest();
        accessToken = userClient.createUser(createUserRequest)
                .extract().jsonPath().get("accessToken");
    }

    @Test
    @DisplayName("Create order without authorization")
    public void createOrderWithoutAuthorized() {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest(validIngredients);
        orderClient.createOrder("", createOrderRequest)
                .statusCode(200)
                .body("name", Matchers.notNullValue())
                .body("order.number", Matchers.notNullValue())
                .body("success", Matchers.equalTo(true));
    }

    @Test
    @DisplayName("Create order with authorization and valid ingredients")
    public void createOrderWithAuthorizedAndValidIngredients() {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest(validIngredients);
        orderClient.createOrder(accessToken, createOrderRequest)
                .statusCode(200)
                .body("name", Matchers.notNullValue())
                .body("order.number", Matchers.notNullValue())
                .body("success", Matchers.equalTo(true));
    }

    @Test
    @DisplayName("Create order with authorization and without ingredients")
    public void errorCreateOrderWithAuthorizedWithoutIngredients() {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest(null);
        orderClient.createOrder(accessToken, createOrderRequest)
                .statusCode(400)
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Create order with authorization and invalid ingredients")
    public void errorCreateOrderWithAuthorizedAndInvalidIngredients() {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest(invalidIngredients);
        orderClient.createOrder(accessToken, createOrderRequest)
                .statusCode(500);
    }

    @After
    @DisplayName("Delete user")
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser((accessToken));
        }
    }
}