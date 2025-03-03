package com.bci.demo.controller;

import com.bci.demo.dto.UsersDto;
import com.bci.demo.request.UserRequest;
import com.bci.demo.service.UserService;
import com.bci.demo.web.response.ApiResponse;
import com.bci.demo.web.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<UserResponse>> save(@Valid @RequestBody UserRequest userRequest) {
        return userService.save(userRequest);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<UsersDto>> findAll() {
        return userService.findAll();
    }

}
