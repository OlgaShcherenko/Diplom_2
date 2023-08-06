package ru.praktikum.diplom_2.dataprovider;

import org.apache.commons.lang3.RandomStringUtils;
import ru.praktikum.diplom_2.pojo.CreateUserRequest;
import ru.praktikum.diplom_2.pojo.ChangeUserDataRequest;

public class UserProvider {
    public static CreateUserRequest getRandomCreateUserRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail(RandomStringUtils.randomAlphabetic(5) + "@yandex.ru");
        createUserRequest.setPassword(RandomStringUtils.randomAlphabetic(5));
        createUserRequest.setName(RandomStringUtils.randomAlphabetic(5));
        return createUserRequest;
    }

    public static CreateUserRequest getRandomCreateUserRequestWithoutEmail() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail(RandomStringUtils.randomAlphabetic(0));
        createUserRequest.setPassword(RandomStringUtils.randomAlphabetic(5));
        createUserRequest.setName(RandomStringUtils.randomAlphabetic(5));
        return createUserRequest;
    }

    public static CreateUserRequest getRandomCreateUserRequestWithoutPassword() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail(RandomStringUtils.randomAlphabetic(5) + "@yandex.ru");
        createUserRequest.setPassword(RandomStringUtils.randomAlphabetic(0));
        createUserRequest.setName(RandomStringUtils.randomAlphabetic(5));
        return createUserRequest;
    }

    public static CreateUserRequest getRandomCreateUserRequestWithoutName() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail(RandomStringUtils.randomAlphabetic(5) + "@yandex.ru");
        createUserRequest.setPassword(RandomStringUtils.randomAlphabetic(5));
        createUserRequest.setName(RandomStringUtils.randomAlphabetic(0));
        return createUserRequest;
    }

    public static ChangeUserDataRequest getRandomChangeUserDataRequest() {
        ChangeUserDataRequest changeUserDataRequest = new ChangeUserDataRequest();
        changeUserDataRequest.setEmail(RandomStringUtils.randomAlphabetic(7) + "@mail.ru");
        changeUserDataRequest.setName(RandomStringUtils.randomAlphabetic(6));
        return changeUserDataRequest;
    }
}