package com.bci.demo.controller;

import com.bci.demo.dto.UserDto;
import com.bci.demo.dto.UsersDto;
import com.bci.demo.mapper.UserMapper;
import com.bci.demo.model.User;
import com.bci.demo.request.UserRequest;
import com.bci.demo.service.UserService;
import com.bci.demo.web.response.ApiResponse;
import com.bci.demo.web.response.UserResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("")
    public ResponseEntity<ApiResponse<UserResponse>> save(@Valid @RequestBody UserRequest userRequest) {
        User user = userMapper.map(userRequest);
        User savedUser = userService.save(user);

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

    @GetMapping("")
    public ResponseEntity<ApiResponse<UsersDto>> findAll() {

        List<User> users = userService.findAll();
        List<UserDto> userDtos = userMapper.map(users);

        UsersDto usersDto = new UsersDto(userDtos);
        ApiResponse<UsersDto> response = new ApiResponse<>(usersDto, HttpStatus.OK.value(),
                "Users fetched successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
