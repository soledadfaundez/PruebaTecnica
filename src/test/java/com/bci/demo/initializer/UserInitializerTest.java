package com.bci.demo.initializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.bci.demo.model.Phone;
import com.bci.demo.model.User;
import com.bci.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

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
        // Configurar el mapa con un solo elemento
        when(userConfig.getData()).thenReturn(Map.of("admin", "Admin User"));

        // Capturar el argumento pasado a userService.save()
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        // Ejecutar la lógica
        userInitializer.run();

        // Verificar que el save fue llamado una vez
        verify(userService, times(1)).save(userCaptor.capture());

        // Obtener el usuario capturado
        User capturedUser = userCaptor.getValue();

        // Verificar los valores del usuario capturado
        assertEquals("Admin User", capturedUser.getName());
        assertEquals("admin@example.com", capturedUser.getEmail());
        assertEquals("Default", capturedUser.getPassword());
        // Si necesitas verificar los teléfonos, también puedes hacerlo aquí
        assertEquals(2, capturedUser.getPhones().size());
        assertEquals("1234567", capturedUser.getPhones().get(0).getNumber());
    }
}