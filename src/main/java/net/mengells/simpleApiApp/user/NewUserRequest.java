package net.mengells.simpleApiApp.user;

public class NewUserRequest {
    public String name;
    public String email;

    public NewUserRequest() {
    }

    public NewUserRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
