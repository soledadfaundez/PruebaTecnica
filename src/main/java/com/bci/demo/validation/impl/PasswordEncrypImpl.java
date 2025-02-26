package com.bci.demo.validation.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bci.demo.validation.PasswordEncryp;

@Component
public class PasswordEncrypImpl implements PasswordEncryp {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Encriptar la contraseña
    @Override
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
