package com.bci.demo.request;

import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class UserRequest {

    @NotNull(message = "{user.name.required}")
    @NotBlank(message = "{user.name.not.blank}")
    private String name;

    @NotNull(message = "{user.email.required}")
    @NotBlank(message = "{user.email.not.blank}")
    private String email;

    @NotNull(message = "{user.password.required}")
    @NotBlank(message = "{user.password.not.blank}")
    private String password;

    @Valid // This will trigger validation for each element in the list
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
