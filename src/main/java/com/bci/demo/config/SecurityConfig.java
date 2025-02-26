package com.bci.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                // Permitir acceso a Swagger UI y documentación
                .antMatchers(
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/configuration/ui",
                        "/swagger-resources/configuration/security")
                .permitAll()

                // Permitir acceso a los endpoints de tu controlador (ajusta la ruta según
                // corresponda)
                .antMatchers("/users/**").permitAll() // Ruta para /users
                // Permitir acceso a la consola H2
                .antMatchers("/h2-console/**").permitAll() // Ruta de H2 console
                .anyRequest().authenticated() // Resto de las rutas requieren autenticación
                .and()
                .formLogin()
                .and()
                .httpBasic(); // O usa otro mecanismo de autenticación si es necesario

        // Habilitar soporte para la consola H2 en un entorno seguro
        http.headers().frameOptions().sameOrigin(); // Necesario para que funcione el iframe de H2 console

        return http.build();
    }
}
