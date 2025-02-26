package com.bci.demo.model;

import java.util.UUID;

// SFC: Se cambia para versiones de Java 17 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "`phone`")
public class Phone {

    // SFC: Configurar la llave como un UUID
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String citycode;

    @Column(nullable = false)
    private String contrycode;

    // SFC: Esta clase contiene la relación a través del User ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Constructor por defecto (No-argument constructor)
    public Phone() {
    }

    // Constructor con todos los parámetros
    public Phone(UUID id, String number, String citycode, String contrycode, User user) {
        this.id = id;
        this.number = number;
        this.citycode = citycode;
        this.contrycode = contrycode;
        this.user = user;
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getContrycode() {
        return contrycode;
    }

    public void setContrycode(String contrycode) {
        this.contrycode = contrycode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
