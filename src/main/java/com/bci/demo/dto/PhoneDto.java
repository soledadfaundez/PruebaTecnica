package com.bci.demo.dto;

import java.util.UUID;

public class PhoneDto {

    private UUID id;
    private String number;
    private String citycode;
    private String contrycode;

    // Constructor por defecto (No-argument constructor)
    public PhoneDto() {
    }

    // Constructor con todos los parámetros
    public PhoneDto(UUID id, String number, String citycode, String contrycode) {
        this.id = id;
        this.number = number;
        this.citycode = citycode;
        this.contrycode = contrycode;
    }

    // Getter para id
    public UUID getId() {
        return id;
    }

    // Setter para id
    public void setId(UUID id) {
        this.id = id;
    }

    // Getter para number
    public String getNumber() {
        return number;
    }

    // Setter para number
    public void setNumber(String number) {
        this.number = number;
    }

    // Getter para citycode
    public String getCitycode() {
        return citycode;
    }

    // Setter para citycode
    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    // Getter para contrycode
    public String getContrycode() {
        return contrycode;
    }

    // Setter para contrycode
    public void setContrycode(String contrycode) {
        this.contrycode = contrycode;
    }
}
