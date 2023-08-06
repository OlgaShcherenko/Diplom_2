import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import ru.praktikum.diplom_2.clients.UserClient;
import ru.praktikum.diplom_2.dataprovider.UserProvider;
import ru.praktikum.diplom_2.pojo.CreateUserRequest;
import ru.praktikum.diplom_2.pojo.LoginUserRequest;

public class LoginUserTest {
    private UserClient userClient = new UserClient();
    private String accessToken;

    @Test
    @DisplayName("Successful authorization under existing user")
    public void userShouldBeLogin() {
        CreateUserRequest createUserRequest = UserProvider.getRandomCreateUserRequest();
        accessToken = userClient.createUser(createUserRequest)
                .extract().jsonPath().get("accessToken");
        LoginUserRequest loginUserRequest = LoginUserRequest.from(createUserRequest);
        userClient.loginUser(loginUserRequest)
                .statusCode(200)
                .body("success", Matchers.equalTo(true))
                .body("accessToken", Matchers.notNullValue())
                .body("refreshToken", Matchers.notNullValue());
    }

    @Test
    @DisplayName("Authorization error with invalid field email")
    public void userWithInvalidEmailUnauthorized() {
        CreateUserRequest createUserRequest = UserProvider.getRandomCreateUserRequest();
        accessToken = userClient.createUser(createUserRequest)
                .extract().jsonPath().get("accessToken");
        LoginUserRequest loginUserRequest = LoginUserRequest.from(createUserRequest);
        loginUserRequest.setEmail(RandomStringUtils.randomAlphabetic(8));
        userClient.loginUser(loginUserRequest)
                .statusCode(401)
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Authorization error with invalid field password")
    public void userWithInvalidPasswordUnauthorized() {
        CreateUserRequest createUserRequest = UserProvider.getRandomCreateUserRequest();
        accessToken = userClient.createUser(createUserRequest)
                .extract().jsonPath().get("accessToken");
        LoginUserRequest loginUserRequest = LoginUserRequest.from(createUserRequest);
        loginUserRequest.setPassword(RandomStringUtils.randomAlphabetic(8));
        userClient.loginUser(loginUserRequest)
                .statusCode(401)
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("email or password are incorrect"));
    }

    @After
    @DisplayName("Delete user")
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser((accessToken));
        }
    }
}