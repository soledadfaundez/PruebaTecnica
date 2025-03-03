package com.bci.demo.service.impl;

import com.bci.demo.dao.UserRepository;
import com.bci.demo.dto.UserDto;
import com.bci.demo.dto.UsersDto;
import com.bci.demo.mapper.UserMapper;
import com.bci.demo.model.User;
import com.bci.demo.request.UserRequest;
import com.bci.demo.service.JwtGenerateService;
import com.bci.demo.service.UserService;
import com.bci.demo.validation.EmailValidator;
import com.bci.demo.validation.PasswordEncryp;
import com.bci.demo.validation.PasswordValidator;
import com.bci.demo.web.response.ApiResponse;
import com.bci.demo.web.response.UserResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private UserMapper userMapper;

    public boolean isEmailUnique(String email) {
        return userRepository.findByEmail(email) == null;
    }

    @Override
    public ResponseEntity<ApiResponse<UserResponse>> save(UserRequest userRequest) {

        User user = userMapper.map(userRequest);

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

        User savedUser = userRepository.save(user);

        // Crear la respuesta con datos
        UserResponse userResponse = new UserResponse(
                savedUser.getId(),
                savedUser.getCreated(),
                savedUser.getModified(),
                savedUser.getLastLogin(),
                savedUser.getToken(),
                savedUser.getIsactive());

        ApiResponse<UserResponse> response = new ApiResponse<>(userResponse, HttpStatus.CREATED.value(),
                "User created successfully");

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<ApiResponse<UsersDto>> findAll() {

        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = userMapper.map(users);

        UsersDto usersDto = new UsersDto(userDtos);
        ApiResponse<UsersDto> response = new ApiResponse<>(usersDto, HttpStatus.OK.value(),
                "Users fetched successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
