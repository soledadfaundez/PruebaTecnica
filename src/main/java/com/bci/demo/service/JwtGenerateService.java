package com.bci.demo.service;

public interface JwtGenerateService {

    /**
     * Genera un token JWT basado en el nombre de usuario.
     * 
     * @param email el nombre de usuario para el cual se generará el token.
     * @return el token JWT generado como una cadena.
     */
    String createToken(String email);

    /**
     * Valida un token JWT y extrae el nombre de usuario del mismo.
     * 
     * @param token el token JWT a validar.
     * @return el nombre de usuario extraído del token si es válido.
     */
    String validateToken(String token);
}
