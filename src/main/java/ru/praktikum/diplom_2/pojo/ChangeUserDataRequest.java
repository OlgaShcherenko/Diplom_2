package ru.praktikum.diplom_2.pojo;

public class ChangeUserDataRequest {
    private String email;
    private String name;

    public ChangeUserDataRequest() {
    }

    public ChangeUserDataRequest(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}