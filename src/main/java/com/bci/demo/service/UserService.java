package com.bci.demo.service;

import com.bci.demo.model.User;
import java.util.List;

public interface UserService {

    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param user el objeto {@link User} a guardar.
     * @return el usuario guardado con el ID generado.
     */
    User save(User user);

    /**
     * Recupera todos los usuarios registrados.
     *
     * @return una lista con todos los usuarios.
     */
    List<User> findAll();
}