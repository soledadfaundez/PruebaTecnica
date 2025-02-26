package com.bci.demo.request;

public class PhoneRequest {

    private String number;
    private String citycode;
    private String contrycode;

    // Constructor por defecto (No-argument constructor)
    public PhoneRequest() {
    }

    // Constructor con todos los parámetros
    public PhoneRequest(String number, String citycode, String contrycode) {
        this.number = number;
        this.citycode = citycode;
        this.contrycode = contrycode;
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