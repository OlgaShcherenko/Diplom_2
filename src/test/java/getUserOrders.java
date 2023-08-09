import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.diplom_2.clients.OrderClient;
import ru.praktikum.diplom_2.clients.UserClient;
import ru.praktikum.diplom_2.dataprovider.UserProvider;
import ru.praktikum.diplom_2.pojo.CreateUserRequest;

public class getUserOrders {
    UserClient userClient = new UserClient();
    OrderClient orderClient = new OrderClient();
    private String accessToken;

    @Before
    @DisplayName("Create user")
    public void setUp() {
        CreateUserRequest createUserRequest = UserProvider.getRandomCreateUserRequest();
        accessToken = userClient.createUser(createUserRequest)
                .extract().jsonPath().get("accessToken");
    }

    @Test
    @DisplayName("Get orders from an authorized user")
    public void getOrderAuthorizedUser() {
        orderClient.getUsersOrders(accessToken)
                .statusCode(200)
                .body("success", Matchers.equalTo(true))
                .body("orders", Matchers.notNullValue())
                .body("total", Matchers.notNullValue())
                .body("totalToday", Matchers.notNullValue());
    }

    @Test
    @DisplayName("Get orders from an unauthorized user")
    public void errorGetOrderUnauthorizedUser() {
        orderClient.getUsersOrders("")
                .statusCode(401)
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("You should be authorised"));
    }
}