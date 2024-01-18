
package com.example.kanbantaskmanager.dtos;

public class UpdateUserDto {
    private String password;
    private String username;
    private String email;

    public UpdateUserDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UpdateUserDto() {
    }

    @Override
    public String toString() {
        return "User [username='%s', email='%s', password='%s']".formatted(username, email, password);
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
