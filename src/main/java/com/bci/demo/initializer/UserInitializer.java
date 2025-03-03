package com.bci.demo.initializer;

import com.bci.demo.request.PhoneRequest;
import com.bci.demo.request.UserRequest;
import com.bci.demo.service.UserService;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//SFC: Crear los usuarios admin al inicio 
@Component
public class UserInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConfig userConfig;

    @Override
    public void run(String... args) {
        userConfig.getData().forEach((alias, name) -> {
            // Crear UserRequest con datos básicos
            UserRequest userRequest = new UserRequest();
            userRequest.setName(name);
            userRequest.setEmail(alias + "@example.com");
            userRequest.setPassword("kjkk&&h%mmEa54");

            // Crear algunos teléfonos asociados al usuario
            PhoneRequest phone1 = new PhoneRequest("1234567", "1", "57");
            PhoneRequest phone2 = new PhoneRequest("7654321", "2", "57");

            userRequest.setPhones(Arrays.asList(phone1, phone2));

            // Llamar al servicio con UserRequest
            userService.save(userRequest);
        });
    }
}

/*
 * SFC: La clase implementa CommandLineRunner, lo que significa que su método
 * run se ejecutará al inicio de la aplicación.
 * Dentro del método run, itera sobre los datos de configuración
 * (userConfig.getData())
 * y, para cada par de alias y nombre, crea un
 * nuevo User con dos números de teléfono (Phone) asociados, luego guarda ese
 * usuario
 * utilizando el servicio userService.
 */