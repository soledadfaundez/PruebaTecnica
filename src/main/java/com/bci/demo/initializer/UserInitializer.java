package com.bci.demo.initializer;

import com.bci.demo.model.Phone;
import com.bci.demo.model.User;
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
            User user = new User();
            user.setName(name);
            user.setEmail(alias + "@example.com");
            user.setPassword("kjkk&&h%mmEa54");

            // SFC: Crear algunos teléfonos asociados al usuario
            Phone phone1 = new Phone(null, "1234567", "1", "57", user);
            Phone phone2 = new Phone(null, "7654321", "2", "57", user);

            user.setPhones(Arrays.asList(phone1, phone2));
            userService.save(user);
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