package com.bci.demo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.bci.demo.dao.UserRepositoryTest;
import com.bci.demo.dto.UserDto;
import com.bci.demo.dto.UsersDto;
import com.bci.demo.mapper.UserMapper;
import com.bci.demo.model.User;
import com.bci.demo.request.UserRequest;
import com.bci.demo.service.JwtGenerateService;
import com.bci.demo.service.impl.UserServiceImpl;
import com.bci.demo.validation.PasswordEncryp;
import com.bci.demo.validation.PasswordValidator;
import com.bci.demo.validation.impl.EmailValidatorImpl;
import com.bci.demo.web.response.ApiResponse;
import com.bci.demo.web.response.UserResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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

    @Mock
    private UserMapper userMapper; // Simulamos el mapper

    @InjectMocks
    private UserServiceImpl userService; // El servicio que estamos probando

    private UserRequest userRequest;
    private User user;

    @BeforeEach
    public void setUp() {
        // Configurar el UserRequest y User para pruebas
        userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");
        userRequest.setName("Test User");
        userRequest.setPassword("passRSword123#kkmaa");

        // Configuración del User a partir de UserRequest
        user = new User();
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setPassword(userRequest.getPassword());
    }

    @Test
    public void testIsEmailUnique_whenEmailNotExists() {
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(null);
        assertTrue(userService.isEmailUnique(userRequest.getEmail()));
    }

    @Test
    public void testIsEmailUnique_whenEmailExists() {
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(user);
        assertFalse(userService.isEmailUnique(userRequest.getEmail()));
    }

    @Test
    public void testSave_whenEmailIsNotUnique() {
        // Simula que el email ya existe en la base de datos
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(new User());

        // Simula el mensaje de error para el email duplicado
        when(messageSource.getMessage("user.email.exists", null, Locale.getDefault()))
                .thenReturn("Email already exists");

        // Simula el mapeo del UserRequest a User
        when(userMapper.map(userRequest)).thenReturn(user); // Aquí mockeamos el mapeo

        // Verifica que se lanza la excepción y que el mensaje sea el esperado
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.save(userRequest); // Llamamos al método save
        });

        assertEquals("Email already exists", exception.getMessage()); // Verifica que el mensaje es el esperado
    }

    @Test
    public void testSave_whenEmailIsNotValid() {
        // Simula que el email no es válido
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(null);
        when(emailValidator.isValidEmail(userRequest.getEmail())).thenReturn(false);

        // Simula el mensaje de error para el email no válido
        when(messageSource.getMessage("user.email.invalid", null, Locale.getDefault()))
                .thenReturn("Email not valid");

        // Simula el mapeo del UserRequest a User
        when(userMapper.map(userRequest)).thenReturn(user); // Aquí mockeamos el mapeo

        // Verifica que se lanza una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.save(userRequest);
        });

        assertEquals("Email not valid", exception.getMessage());
    }

    @Test
    public void testSave_whenPasswordIsNotValid() {
        // Simula que el email es válido y no existe
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(null);
        when(emailValidator.isValidEmail(userRequest.getEmail())).thenReturn(true);
        when(passwordValidator.isValidPassword(userRequest.getPassword())).thenReturn(false);

        // Simula el mensaje de error para la contraseña no válida
        when(messageSource.getMessage("user.password.msj", null, Locale.getDefault()))
                .thenReturn("Password not valid");

        // Simula el mapeo del UserRequest a User
        when(userMapper.map(userRequest)).thenReturn(user); // Aquí mockeamos el mapeo

        // Verifica que se lanza una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.save(userRequest);
        });

        assertEquals("Password not valid", exception.getMessage());
    }

    @Test
    public void testSave_whenValidData() {
        // Crear un UserRequest con los datos necesarios
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");
        userRequest.setName("Test User");
        userRequest.setPassword("passRSword123#kkmaa");

        // Simula que el email no existe, es válido y la contraseña es válida
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(null);
        when(emailValidator.isValidEmail(userRequest.getEmail())).thenReturn(true);
        when(passwordValidator.isValidPassword(userRequest.getPassword())).thenReturn(true);
        when(jwtGenerate.createToken(userRequest.getEmail())).thenReturn("generated_token");
        when(passwordEncryp.encodePassword(userRequest.getPassword())).thenReturn("encrypted_password");

        // Simula que el save del repositorio retorna un User
        User savedUser = new User();
        savedUser.setEmail(userRequest.getEmail());
        savedUser.setName(userRequest.getName());
        savedUser.setPassword("encrypted_password");
        savedUser.setToken("generated_token");
        savedUser.setIsactive(true);
        savedUser.setCreated(LocalDateTime.now());
        savedUser.setModified(LocalDateTime.now());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Simula el mapeo del UserRequest a User
        when(userMapper.map(userRequest)).thenReturn(user); // Aquí mockeamos el mapeo

        // Llamamos al método save con UserRequest
        ResponseEntity<ApiResponse<UserResponse>> response = userService.save(userRequest);

        // Verifica que la respuesta no sea nula
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verifica los datos de la respuesta
        ApiResponse<UserResponse> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertEquals("User created successfully", apiResponse.getMensaje());
        assertNotNull(apiResponse.getData());
        assertEquals("generated_token", apiResponse.getData().getToken());
        assertTrue(apiResponse.getData().isActive());
        assertNotNull(apiResponse.getData().getCreated());
        assertNotNull(apiResponse.getData().getModified());
    }

    @Test
    public void testFindAll() {
        // Crear el DTO de User
        UserDto userDto = new UserDto();
        userDto.setId(UUID.randomUUID());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());

        List<User> users = Arrays.asList(user);

        List<UserDto> userDtos = Arrays.asList(userDto);

        // Crear el ApiResponse que contiene un UsersDto
        UsersDto usersDto = new UsersDto();
        usersDto.setUserDtos(Collections.singletonList(userDto)); // Una lista con un solo UserDto

        // Simula el comportamiento del userMapper para mapear los usuarios a UserDto
        when(userMapper.map(users)).thenReturn(userDtos);

        // Simula la respuesta de la base de datos
        when(userRepository.findAll()).thenReturn(users);

        // Simula la respuesta de ApiResponse
        ApiResponse<UsersDto> apiResponse = new ApiResponse<>(new UsersDto(userDtos), HttpStatus.OK.value(),
                "Users fetched successfully");

        // Llamamos al método findAll del servicio
        ResponseEntity<ApiResponse<UsersDto>> response = userService.findAll();

        // Verificamos que la respuesta tenga el estado esperado
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificamos que la respuesta contenga los datos esperados
        assertNotNull(response.getBody());
        assertEquals("Users fetched successfully", response.getBody().getMensaje());
        assertEquals(1, response.getBody().getData().getUserDtos().size());
    }
}
