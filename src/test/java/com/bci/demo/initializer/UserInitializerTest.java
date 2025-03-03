package com.bci.demo.initializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.bci.demo.request.UserRequest;
import com.bci.demo.service.UserService;
import com.bci.demo.web.response.ApiResponse;
import com.bci.demo.web.response.UserResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UserInitializerTest {

    @Mock
    private UserService userService; // Simulamos el servicio UserService

    @Mock
    private UserConfig userConfig; // Simulamos la clase UserConfig

    @InjectMocks
    private UserInitializer userInitializer; // El componente que estamos probando

    @BeforeEach
    public void setUp() {
        // Preparar un Map de datos de configuración simulado para los usuarios
        Map<String, String> userData = Map.of(
                "admin", "Admin User");

        // Simulamos que el método getData devuelve el mapa de datos
        when(userConfig.getData()).thenReturn(userData);
    }

    @Test
    public void testRun_createsUsers() {
        // Configurar el mapa con un solo usuario simulado
        when(userConfig.getData()).thenReturn(Map.of("admin", "Admin User"));

        // Capturar el argumento pasado a userService.save()
        ArgumentCaptor<UserRequest> userRequestCaptor = ArgumentCaptor.forClass(UserRequest.class);

        // Simulación del UserResponse esperado
        UserResponse userResponse = new UserResponse(
                UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now(),
                LocalDateTime.now(), "sample-token", true);

        // Simulación del ApiResponse esperado
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>(userResponse, HttpStatus.CREATED.value(),
                "User created successfully");

        // Simular la respuesta de userService.save() con un UserRequest
        when(userService.save(any(UserRequest.class)))
                .thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.CREATED));

        // Ejecutar la lógica
        userInitializer.run();

        // Verificar que el save fue llamado una vez con el UserRequest esperado
        verify(userService, times(1)).save(userRequestCaptor.capture());

        // Obtener el UserRequest capturado
        UserRequest capturedUserRequest = userRequestCaptor.getValue();

        // Verificar los valores del UserRequest capturado
        assertEquals("Admin User", capturedUserRequest.getName());
        assertEquals("admin@example.com", capturedUserRequest.getEmail());
        assertEquals("kjkk&&h%mmEa54", capturedUserRequest.getPassword());
    }
}