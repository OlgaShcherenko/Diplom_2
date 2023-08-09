import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import ru.praktikum.diplom_2.clients.UserClient;
import ru.praktikum.diplom_2.dataprovider.UserProvider;
import ru.praktikum.diplom_2.pojo.CreateUserRequest;

public class CreateUserTest {
    private UserClient userClient = new UserClient();
    private String accessToken;

    @Test
    @DisplayName("Successful creation of unique user")
    public void newUserShouldBeCreated() {
        CreateUserRequest createUserRequest = UserProvider.getRandomCreateUserRequest();
        accessToken = userClient.createUser(createUserRequest)
                .statusCode(200)
                .body("success", Matchers.equalTo(true))
                .body("user.email", Matchers.equalTo(createUserRequest.getEmail().toLowerCase()))
                .body("user.name", Matchers.equalTo(createUserRequest.getName()))
                .body("accessToken", Matchers.notNullValue())
                .body("refreshToken", Matchers.notNullValue())
                .extract().jsonPath().get("accessToken");
    }

    @Test
    @DisplayName("Error creating existing user")
    public void existingUserShouldNotBeCreated() {
        CreateUserRequest createUserRequest = UserProvider.getRandomCreateUserRequest();
        accessToken = userClient.createUser(createUserRequest)
                .extract().jsonPath().get("accessToken");
        userClient.createUser(createUserRequest)
                .statusCode(403)
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("User already exists"));
    }

    @Test
    @DisplayName("Error creating user without field email")
    public void userWithoutEmailShouldNotBeCreated() {
        CreateUserRequest createUserRequest = UserProvider.getRandomCreateUserRequestWithoutEmail();
        userClient.createUser(createUserRequest)
                .statusCode(403)
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Error creating user without password")
    public void userWithoutPasswordShouldNotBeCreated() {
        CreateUserRequest createUserRequest = UserProvider.getRandomCreateUserRequestWithoutPassword();
        userClient.createUser(createUserRequest)
                .statusCode(403)
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Error creating user without name")
    public void userWithoutNameShouldNotBeCreated() {
        CreateUserRequest createUserRequest = UserProvider.getRandomCreateUserRequestWithoutName();
        userClient.createUser(createUserRequest)
                .statusCode(403)
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("Email, password and name are required fields"));
    }

    @After
    @DisplayName("Delete user")
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser((accessToken));
        }
    }
}