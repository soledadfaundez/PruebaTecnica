package com.bci.demo.mapper;

import com.bci.demo.dto.PhoneDto;
import com.bci.demo.dto.UserDto;
import com.bci.demo.model.Phone;
import com.bci.demo.model.User;
import com.bci.demo.request.UserRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    // SFC: user a userDTO
    public UserDto map(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setIsactive(user.isIsactive());

        // SFC: Mapear lista de teléfonos
        if (user.getPhones() != null) {
            userDto.setPhones(user.getPhones().stream()
                    .map(this::mapPhone)
                    .collect(Collectors.toList()));
        }

        return userDto;
    }

    // SFC: userDTO a user
    public User map(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }

    // SFC: UserRequest a User
    public User map(UserRequest userRequest) {
        User user = new User();
        user.setId(null);
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        // SFC: Mapear lista de teléfonos
        if (userRequest.getPhones() != null) {

            List<Phone> phones = userRequest.getPhones().stream()
                    .map(phoneReq -> new Phone(null, phoneReq.getNumber(), phoneReq.getCitycode(),
                            phoneReq.getContrycode(), user))
                    .collect(Collectors.toList());

            user.setPhones(phones);
        }
        return user;
    }

    // SFC: UserRequest a User
    public User map(UUID id, UserRequest userRequest) {
        User user = new User();
        user.setId(id);
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        // user.setToken(null);

        if (userRequest.getPhones() != null) {
            List<Phone> phones = userRequest.getPhones().stream()
                    .map(phoneReq -> new Phone(UUID.randomUUID(), phoneReq.getNumber(), phoneReq.getCitycode(),
                            phoneReq.getContrycode(), user))
                    .collect(Collectors.toList());
            user.setPhones(phones);
        }

        return user;
    }

    public List<UserDto> map(List<User> users) {
        return users
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public PhoneDto mapPhone(Phone phone) {
        PhoneDto phoneDto = new PhoneDto();
        phoneDto.setId(phone.getId());
        phoneDto.setNumber(phone.getNumber());
        phoneDto.setCitycode(phone.getCitycode());
        phoneDto.setContrycode(phone.getContrycode());
        return phoneDto;
    }
}
