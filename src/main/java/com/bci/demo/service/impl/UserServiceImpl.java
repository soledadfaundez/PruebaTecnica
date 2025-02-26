package com.bci.demo.service.impl;

import com.bci.demo.dao.UserRepository;
import com.bci.demo.model.User;
import com.bci.demo.service.JwtGenerateService;
import com.bci.demo.service.UserService;
import com.bci.demo.validation.EmailValidator;
import com.bci.demo.validation.PasswordEncryp;
import com.bci.demo.validation.PasswordValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtGenerateService jwtGenerate;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private PasswordEncryp passwordEncryp;

    @Autowired
    private MessageSource messageSource;

    public boolean isEmailUnique(String email) {
        return userRepository.findByEmail(email) == null;
    }

    @Override
    public User save(User user) {

        // SFC: Validar que el email no esté repetido
        if (!isEmailUnique(user.getEmail())) {
            throw new IllegalArgumentException(
                    messageSource.getMessage("user.email.exists", null, Locale.getDefault()));
        }

        if (!emailValidator.isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException(
                    messageSource.getMessage("user.email.invalid", null, Locale.getDefault()));
        }

        // SFC: Validar complejidad de la clave.
        if (!passwordValidator.isValidPassword(user.getPassword())) {
            throw new IllegalArgumentException(
                    messageSource.getMessage("user.password.msj", null, Locale.getDefault()));
        }

        // SFC: Completar los datos internos
        LocalDateTime now = LocalDateTime.now();
        String token = jwtGenerate.createToken(user.getEmail());

        // SFC: Encriptar la clave
        String passwordEncript = passwordEncryp.encodePassword(user.getPassword());

        user.setPassword(passwordEncript);
        user.setToken(token);
        user.setCreated(now);
        user.setModified(now);
        user.setLastLogin(now);
        user.setIsactive(true);

        return userRepository.save(user);

    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
