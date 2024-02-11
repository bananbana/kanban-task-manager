package com.example.kanbantaskmanager.dtos;

public class UpdatePasswordDto {
    private String newPassword;
    private UpdateUserDto userDto;

    public UpdatePasswordDto(UpdateUserDto userDto, String newPassword) {
        this.userDto = userDto;
        this.newPassword = newPassword;
    }

    public UpdatePasswordDto() {
    }

    @Override
    public String toString() {
        return "User [username='%s']".formatted(userDto.getUsername());
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public UpdateUserDto getUserDto() {
        return this.userDto;
    }

    public void setUserDto(UpdateUserDto userDto) {
        this.userDto = userDto;
    }
}
