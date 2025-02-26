package com.bci.demo.dao;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bci.demo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository; // Mock del repositorio

    private User user;
    private UUID userId;

    @BeforeEach
    public void setUp() {
        // Inicialización de los objetos
        userId = UUID.randomUUID(); // Crear un UUID aleatorio
        user = new User();
        user.setId(userId);
        user.setEmail("test@example.com");
        user.setName("Test User");
    }

    @Test
    public void testFindByEmail() {
        // Configuración del comportamiento del mock
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        // Llamar al método del repositorio
        User result = userRepository.findByEmail("test@example.com");

        // Realizar las aserciones
        assertEquals(userId, result.getId());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("Test User", result.getName());
    }
}