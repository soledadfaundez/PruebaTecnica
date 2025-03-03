package com.bci.demo.service;

import com.bci.demo.dto.UsersDto;
import com.bci.demo.model.User;
import com.bci.demo.request.UserRequest;
import com.bci.demo.web.response.ApiResponse;
import com.bci.demo.web.response.UserResponse;

import org.springframework.http.ResponseEntity;

public interface UserService {

    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param user el objeto {@link User} a guardar.
     * @return el usuario guardado con el ID generado.
     */
    ResponseEntity<ApiResponse<UserResponse>> save(UserRequest user);

    /**
     * Recupera todos los usuarios registrados.
     *
     * @return una lista con todos los usuarios.
     */
    ResponseEntity<ApiResponse<UsersDto>> findAll();
}