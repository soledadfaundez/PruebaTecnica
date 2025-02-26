package com.bci.demo.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.bci.demo.dao.UserRepositoryTest;
import com.bci.demo.model.User;
import com.bci.demo.service.JwtGenerateService;
import com.bci.demo.service.impl.UserServiceImpl;
import com.bci.demo.validation.PasswordEncryp;
import com.bci.demo.validation.PasswordValidator;
import com.bci.demo.validation.impl.EmailValidatorImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private com.bci.demo.dao.UserRepository userRepository; // Simulamos el repositorio de usuarios

    @Mock
    private JwtGenerateService jwtGenerate; // Simulamos el servicio JwtGenerate

    @Mock
    private EmailValidatorImpl emailValidator; // Simulamos el validador de emails

    @Mock
    private PasswordValidator passwordValidator;

    @Mock
    private PasswordEncryp passwordEncryp;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private UserServiceImpl userService; // El servicio que estamos probando

    private User user;

    @BeforeEach
    public void setUp() {
        // Configuramos el usuario para las pruebas
        user = new User();
        user.setEmail("test@example.com");
        user.setName("Test User");
        user.setPassword("passRSword123#kkmaa");

        // Inicializamos los mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsEmailUnique_whenEmailNotExists() {
        // Simula que el email no existe en la base de datos
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        // Verifica que el email es único
        assertTrue(userService.isEmailUnique(user.getEmail()));
    }

    @Test
    public void testIsEmailUnique_whenEmailExists() {
        // Simula que el email ya existe en la base de datos
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        // Verifica que el email no es único
        assertFalse(userService.isEmailUnique(user.getEmail()));
    }

    @Test
    public void testSave_whenEmailIsNotUnique() {
        // Simula que el email ya existe en la base de datos
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        // Simula el mensaje de error para el email duplicado
        when(messageSource.getMessage("user.email.exists", null, Locale.getDefault()))
                .thenReturn("Email already exists");

        // Verifica que se lanza una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.save(user);
        });

        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    public void testSave_whenEmailIsNotValid() {
        // Simula que el email no es válido
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(emailValidator.isValidEmail(user.getEmail())).thenReturn(false);

        // Simula el mensaje de error para el email no válido
        when(messageSource.getMessage("user.email.invalid", null, Locale.getDefault()))
                .thenReturn("Email not valid");

        // Verifica que se lanza una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.save(user);
        });

        assertEquals("Email not valid", exception.getMessage());
    }

    @Test
    public void testSave_whenPasswordIsNotValid() {
        // Simula que el email es válido y no existe
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(emailValidator.isValidEmail(user.getEmail())).thenReturn(true);
        when(passwordValidator.isValidPassword(user.getPassword())).thenReturn(false);

        // Simula el mensaje de error para la contraseña no válida
        when(messageSource.getMessage("user.password.msj", null, Locale.getDefault()))
                .thenReturn("Password not valid");

        // Verifica que se lanza una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.save(user);
        });

        assertEquals("Password not valid", exception.getMessage());
    }

    @Test
    public void testSave_whenValidData() {
        // Simula que el email no existe, es válido y la contraseña es válida
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(emailValidator.isValidEmail(user.getEmail())).thenReturn(true);
        when(passwordValidator.isValidPassword(user.getPassword())).thenReturn(true);
        when(jwtGenerate.createToken(user.getEmail())).thenReturn("generated_token");
        when(passwordEncryp.encodePassword(user.getPassword())).thenReturn("encrypted_password");
        when(userRepository.save(user)).thenReturn(user);

        // Llamamos al método save
        User savedUser = userService.save(user);

        // Verifica que el usuario fue guardado y que el token fue generado
        assertNotNull(savedUser);
        assertEquals("generated_token", savedUser.getToken());
        assertEquals("encrypted_password", savedUser.getPassword());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertTrue(savedUser.isIsactive());
        assertNotNull(savedUser.getCreated());
        assertNotNull(savedUser.getModified());
    }

    @Test
    public void testFindAll() {
        // Simula la respuesta del repositorio
        when(userRepository.findAll()).thenReturn(List.of(user));

        // Llamamos al método findAll
        List<User> users = userService.findAll();

        // Verifica que se ha recuperado el usuario
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(user.getEmail(), users.get(0).getEmail());
    }
}
