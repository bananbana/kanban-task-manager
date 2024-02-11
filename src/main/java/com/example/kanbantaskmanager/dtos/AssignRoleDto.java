package com.example.kanbantaskmanager.dtos;

public class AssignRoleDto {

    private Long roleId;

    public AssignRoleDto() {

    }

    public AssignRoleDto(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}