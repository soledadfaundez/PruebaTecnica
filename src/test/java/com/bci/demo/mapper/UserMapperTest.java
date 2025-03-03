package com.bci.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bci.demo.model.Phone;
import com.bci.demo.model.User;
import com.bci.demo.dto.PhoneDto;
import com.bci.demo.dto.UserDto;
import com.bci.demo.request.PhoneRequest;
import com.bci.demo.request.UserRequest;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    private UserMapper userMapper;

    @Mock
    private Phone phone1;

    @Mock
    private Phone phone2;

    private User user;
    private UserRequest userRequest;
    private PhoneRequest phoneDto1;
    private PhoneRequest phoneDto2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userMapper = new UserMapper();

        // Crear un usuario de ejemplo
        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setIsactive(true);

        // Crear algunos teléfonos
        phone1 = new Phone(UUID.randomUUID(), "1234567", "1", "57", user);
        phone2 = new Phone(UUID.randomUUID(), "7654321", "2", "57", user);

        user.setPhones(List.of(phone1, phone2));

        // Crear UserRequest para pruebas de mapeo
        userRequest = new UserRequest();
        userRequest.setName("Jane Doe");
        userRequest.setEmail("jane.doe@example.com");
        userRequest.setPassword("password123");

        // Crear PhoneDto
        phoneDto1 = new PhoneRequest();
        phoneDto1.setNumber(phone1.getNumber());
        phoneDto1.setCitycode(phone1.getCitycode());
        phoneDto1.setContrycode(phone1.getContrycode());

        phoneDto2 = new PhoneRequest();
        phoneDto2.setNumber(phone2.getNumber());
        phoneDto2.setCitycode(phone2.getCitycode());
        phoneDto2.setContrycode(phone2.getContrycode());

        userRequest.setPhones(List.of(phoneDto1, phoneDto2));

    }

    @Test
    public void testMapUserToUserDto() {
        UserDto userDto = userMapper.map(user);

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.isIsactive(), userDto.getIsactive());
        assertEquals(2, userDto.getPhones().size()); // Verificar que tiene 2 teléfonos mapeados
        assertEquals(phone1.getNumber(), userDto.getPhones().get(0).getNumber());
        assertEquals(phone2.getNumber(), userDto.getPhones().get(1).getNumber());
    }

    @Test
    public void testMapUserDtoToUser() {
        UserDto userDto = new UserDto();
        userDto.setId(UUID.randomUUID());
        userDto.setName("Jane Doe");
        userDto.setEmail("jane.doe@example.com");

        User userFromDto = userMapper.map(userDto);

        assertEquals(userDto.getId(), userFromDto.getId());
        assertEquals(userDto.getName(), userFromDto.getName());
        assertEquals(userDto.getEmail(), userFromDto.getEmail());
    }

    @Test
    public void testMapUserRequestToUser() {
        User userFromRequest = userMapper.map(userRequest);

        assertEquals(userRequest.getName(), userFromRequest.getName());
        assertEquals(userRequest.getEmail(), userFromRequest.getEmail());
        assertEquals(userRequest.getPassword(), userFromRequest.getPassword());
        assertEquals(2, userFromRequest.getPhones().size()); // No se han añadido teléfonos en este test
    }

    @Test
    public void testMapUserRequestToUserWithPhones() {
        // Asignamos teléfonos a UserRequest
        userRequest.setPhones(List.of(
                new com.bci.demo.request.PhoneRequest("1234567", "1", "57"),
                new com.bci.demo.request.PhoneRequest("7654321", "2", "57")));

        User userFromRequest = userMapper.map(userRequest);

        assertEquals(2, userFromRequest.getPhones().size());
        assertEquals("1234567", userFromRequest.getPhones().get(0).getNumber());
        assertEquals("7654321", userFromRequest.getPhones().get(1).getNumber());
    }

    @Test
    public void testMapListUserToUserDtoList() {
        List<User> users = List.of(user);

        List<UserDto> userDtos = userMapper.map(users);

        assertEquals(1, userDtos.size());
        assertEquals(user.getId(), userDtos.get(0).getId());
    }

    @Test
    public void testMapPhoneToPhoneDto() {
        PhoneDto phoneDto = userMapper.mapPhone(phone1);

        assertEquals(phone1.getId(), phoneDto.getId());
        assertEquals(phone1.getNumber(), phoneDto.getNumber());
        assertEquals(phone1.getCitycode(), phoneDto.getCitycode());
        assertEquals(phone1.getContrycode(), phoneDto.getContrycode());
    }
}