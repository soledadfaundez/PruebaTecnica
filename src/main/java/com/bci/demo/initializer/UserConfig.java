package com.bci.demo.initializer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "user")
public class UserConfig {

    private Map<String, String> data;

    // Constructor por defecto (No-argument constructor)
    public UserConfig() {
    }

    // Getter para data
    public Map<String, String> getData() {
        return data;
    }

    // Setter para data
    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
