package com.bci.demo.validation;

public interface PasswordEncryp {
    /**
     * Encripta una contraseña en texto plano.
     * 
     * @param rawPassword La contraseña en texto plano.
     * @return La contraseña encriptada.
     */
    String encodePassword(String rawPassword);
}
