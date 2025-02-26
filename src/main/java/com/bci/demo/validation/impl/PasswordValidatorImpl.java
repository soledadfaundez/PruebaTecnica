package com.bci.demo.validation.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bci.demo.validation.PasswordValidator;

@Component
public class PasswordValidatorImpl implements PasswordValidator {

    // Expresión regular para validar el correo
    @Value("${password.regex}")
    private String passwordRegex;

    // Método para validar el correo
    @Override
    public boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        return password.matches(passwordRegex);
    }

}
