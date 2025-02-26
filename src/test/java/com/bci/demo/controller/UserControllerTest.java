package com.bci.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bci.demo.dto.UserDto;
import com.bci.demo.dto.UsersDto;
import com.bci.demo.mapper.UserMapper;
import com.bci.demo.model.User;
import com.bci.demo.request.UserRequest;
import com.bci.demo.service.UserService;
import com.bci.demo.web.response.ApiResponse;
import com.bci.demo.web.response.UserResponse;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;// Simulamos el servicio

    @Mock
    private UserMapper userMapper; // Simulamos el mapper

    @InjectMocks
    private UserController userController; // El controlador con las dependencias inyectadas

    private UserRequest userRequest;
    private User user;
    private UserResponse userResponse;
    private UserDto userDto;
    private UUID userId;

    @BeforeEach
    public void setUp() {

        LocalDateTime now = LocalDateTime.now();
        userId = UUID.randomUUID(); // Generar un UUID aleatorio
        userRequest = new UserRequest();
        user = new User();
        user.setId(userId);
        user.setCreated(now);
        user.setModified(now);
        user.setLastLogin(now);
        user.setToken("sample-token");
        user.setIsactive(true);

        userResponse = new UserResponse(
                user.getId(),
                user.getCreated(),
                user.getModified(),
                user.getLastLogin(),
                user.getToken(),
                user.getIsactive());

        userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName("Test User");
    }

    @Test
    public void testSave() {
        // Simula el comportamiento de las dependencias
        when(userMapper.map(userRequest)).thenReturn(user);
        when(userService.save(user)).thenReturn(user);

        // Llama al método del controlador
        ResponseEntity<ApiResponse<UserResponse>> response = userController.save(userRequest);

        // Realiza las aserciones
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User created successfully", response.getBody().getMensaje());
        assertEquals(user.getId(), response.getBody().getData().getId());
    }

    @Test
    public void testFindAll() {
        when(userService.findAll()).thenReturn(Collections.singletonList(user));
        when(userMapper.map(Mockito.anyList())).thenReturn(Collections.singletonList(userDto));

        ResponseEntity<ApiResponse<UsersDto>> response = userController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Users fetched successfully", response.getBody().getMensaje());
        assertEquals(1, response.getBody().getData().getUserDtos().size());
        assertEquals(userId, response.getBody().getData().getUserDtos().get(0).getId());
    }
}