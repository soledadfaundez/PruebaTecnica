package com.bci.demo.request;

import java.util.List;

public class UserRequest {

    private String name;
    private String email;
    private String password;
    private List<PhoneRequest> phones; // SFC: Lista de teléfonos opcional

    // Constructor por defecto (No-argument constructor)
    public UserRequest() {
    }

    // Constructor con todos los parámetros
    public UserRequest(String name, String email, String password, List<PhoneRequest> phones) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phones = phones;
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

    // Getter para password
    public String getPassword() {
        return password;
    }

    // Setter para password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter para phones
    public List<PhoneRequest> getPhones() {
        return phones;
    }

    // Setter para phones
    public void setPhones(List<PhoneRequest> phones) {
        this.phones = phones;
    }
}
