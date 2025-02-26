package com.bci.demo.validation.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bci.demo.validation.EmailValidator;

@Component
public class EmailValidatorImpl implements EmailValidator {

    // Expresión regular para validar el correo
    @Value("${email.regex}")
    private String EMAIL_REGEX;

    private Pattern pattern;

    @PostConstruct
    public void init() {
        // Inicializa el patrón con el valor de EMAIL_REGEX
        pattern = Pattern.compile(EMAIL_REGEX);
    }

    // Método para validar el correo
    public boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
