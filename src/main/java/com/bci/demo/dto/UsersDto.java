package com.bci.demo.dto;

import java.util.List;

public class UsersDto {

    List<UserDto> userDtos;

    // Constructor por defecto (No-argument constructor)
    public UsersDto() {
    }

    // Constructor con todos los parámetros
    public UsersDto(List<UserDto> userDtos) {
        this.userDtos = userDtos;
    }

    // Getter para userDtos
    public List<UserDto> getUserDtos() {
        return userDtos;
    }

    // Setter para userDtos
    public void setUserDtos(List<UserDto> userDtos) {
        this.userDtos = userDtos;
    }   
}
