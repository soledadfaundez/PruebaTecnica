package com.bci.demo.service.impl;

import com.bci.demo.dao.UserRepository;
import com.bci.demo.model.User;
import com.bci.demo.service.JwtGenerateService;
import com.bci.demo.service.UserService;
import com.bci.demo.validation.EmailValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtGenerateService jwtGenerate;

    @Autowired
    private EmailValidator emailValidator;

    public boolean isEmailUnique(String email) {
        return userRepository.findByEmail(email) == null;
    }

    @Override
    public User save(User user) {

        // SFC: Validar que el email no esté repetido
        if (!isEmailUnique(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (!emailValidator.isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email not valid");
        }

        // SFC: Completar los datos internos
        LocalDateTime now = LocalDateTime.now();
        String token = jwtGenerate.createToken(user.getEmail());

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
