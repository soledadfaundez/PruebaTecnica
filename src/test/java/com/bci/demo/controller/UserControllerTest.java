package com.bci.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
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
    private ApiResponse<UserResponse> apiResponse;
    private UserDto userDto;
    private UUID userId;

    @BeforeEach
    public void setUp() {

        LocalDateTime now = LocalDateTime.now();
        userId = UUID.randomUUID(); // Generar un UUID aleatorio

        // Crear UserRequest simulado
        userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("Test123!");

        // Crear User simulado
        user = new User();
        user.setId(userId);
        user.setCreated(now);
        user.setModified(now);
        user.setLastLogin(now);
        user.setToken("sample-token");
        user.setIsactive(true);

        // Crear UserResponse simulado
        userResponse = new UserResponse(
                user.getId(),
                user.getCreated(),
                user.getModified(),
                user.getLastLogin(),
                user.getToken(),
                user.getIsactive());

        // Crear ApiResponse simulado
        apiResponse = new ApiResponse<>(userResponse, HttpStatus.CREATED.value(), "User created successfully");

        // Crear UserDto simulado
        userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName("Test User");
    }

    @Test
    public void testSave() {

        // Simula el comportamiento del servicio
        when(userService.save(userRequest)).thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.CREATED));

        // Llama al método del controlador
        ResponseEntity<ApiResponse<UserResponse>> response = userController.save(userRequest);

        // Verifica el estado HTTP
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verifica que el mensaje sea correcto
        assertEquals("User created successfully", response.getBody().getMensaje());

        // Verifica que los datos sean los esperados
        assertEquals(user.getId(), response.getBody().getData().getId());
        assertEquals(user.getToken(), response.getBody().getData().getToken());
    }

    @Test
    public void testFindAll() {
        // Simula el comportamiento del servicio y del mapeo
        List<UserDto> userDtoList = Collections.singletonList(userDto);

        // Crear objeto UsersDto
        UsersDto usersDto = new UsersDto(userDtoList);

        // Crear ApiResponse con UsersDto
        ApiResponse<UsersDto> apiResponse = new ApiResponse<>(usersDto, HttpStatus.OK.value(),
                "Users fetched successfully");

        // Simula la respuesta completa del servicio
        when(userService.findAll()).thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.OK));

        // Llama al método del controlador
        ResponseEntity<ApiResponse<UsersDto>> response = userController.findAll();

        // Verifica el estado HTTP
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifica que el mensaje sea correcto
        assertEquals("Users fetched successfully", response.getBody().getMensaje());

        // Verifica que los datos sean correctos
        assertEquals(1, response.getBody().getData().getUserDtos().size());
        assertEquals(userId, response.getBody().getData().getUserDtos().get(0).getId());
    }
}