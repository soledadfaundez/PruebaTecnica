package com.bci.demo.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ApiResponse<T> {

    // SFC: Omitir este campo si es null
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mensaje;

    // SFC: Omitir este campo si es null
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private Integer statusCode;

    // Constructor para casos de error
    public ApiResponse(String mensaje, int statusCode) {
        this.mensaje = mensaje;
        this.data = null; // No se incluye el campo data en los errores
        this.statusCode = statusCode;
    }

    // Constructor para éxito
    public ApiResponse(T data, Integer statusCode, String mensaje) {
        this.data = data;
        this.mensaje = mensaje;
        this.statusCode = statusCode;
    }

    public ApiResponse() {
    }

    // Getters y setters
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer ok) {
        this.statusCode = ok;
    }
}