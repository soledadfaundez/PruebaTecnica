package com.bci.demo.dto;

import java.util.List;
import java.util.UUID;

public class UserDto {
    private UUID id;
    private String name;
    private String email;
    private boolean isactive;
    private List<PhoneDto> phones; // SFC : Lista de teléfonos del usuario

    // Constructor por defecto (No-argument constructor)
    public UserDto() {
    }

    // Constructor con todos los parámetros
    public UserDto(UUID id, String name, String email, boolean isactive, List<PhoneDto> phones) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.isactive = isactive;
        this.phones = phones;
    }

    // Getter para id
    public UUID getId() {
        return id;
    }

    // Setter para id
    public void setId(UUID id) {
        this.id = id;
    }

    // Getter para name
    public String getName() {
        return name;
    }

    // Setter para name
    public void setName(String name) {
        this.name = name;
    }

    // Getter para email
    public String getEmail() {
        return email;
    }

    // Setter para email
    public void setEmail(String email) {
        this.email = email;
    }

    // Setter
    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    public boolean getIsactive() {
        return isactive;
    }

    // Getter para phones
    public List<PhoneDto> getPhones() {
        return phones;
    }

    // Setter para phones
    public void setPhones(List<PhoneDto> phones) {
        this.phones = phones;
    }

}
