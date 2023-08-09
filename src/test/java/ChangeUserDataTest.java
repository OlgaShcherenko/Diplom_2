import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.diplom_2.clients.UserClient;
import ru.praktikum.diplom_2.dataprovider.UserProvider;
import ru.praktikum.diplom_2.pojo.CreateUserRequest;
import ru.praktikum.diplom_2.pojo.ChangeUserDataRequest;

public class ChangeUserDataTest {
    private UserClient userClient = new UserClient();
    private String accessToken;

    @Before
    @DisplayName("Create user")
    public void setUp() {
        CreateUserRequest createUserRequest = UserProvider.getRandomCreateUserRequest();
        accessToken = userClient.createUser(createUserRequest)
                .extract().jsonPath().get("accessToken");
    }

    @Test
    @DisplayName("Changing user data with authorization")
    public void authorizedUserDataShouldBeChange() {
        ChangeUserDataRequest changeUserDataRequest = UserProvider.getRandomChangeUserDataRequest();
        userClient.changeDataUser(accessToken, changeUserDataRequest)
                .statusCode(200)
                .body("success", Matchers.equalTo(true))
                .body("user.email", Matchers.equalTo(changeUserDataRequest.getEmail().toLowerCase()))
                .body("user.name", Matchers.equalTo(changeUserDataRequest.getName()));
    }

    @Test
    @DisplayName("Changing user data without authorization")
    public void unauthorizedUserDataShouldNotBeChange() {
        ChangeUserDataRequest changeUserDataRequest = UserProvider.getRandomChangeUserDataRequest();
        userClient.changeDataUser("", changeUserDataRequest)
                .statusCode(401)
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("You should be authorised"));
    }

    @After
    @DisplayName("Delete user")
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser((accessToken));
        }
    }
}