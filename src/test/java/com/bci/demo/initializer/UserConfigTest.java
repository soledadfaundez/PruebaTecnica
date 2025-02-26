package com.bci.demo.initializer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest // cargar el contexto de Spring, inyectar las propiedades y verificar que se
                // comporta correctamente
public class UserConfigTest {

    @Autowired
    private UserConfig userConfig; // Inyectar UserConfig para que Spring se encargue de su configuración

    @BeforeEach
    public void setUp() {
        // No es necesario configurar nada aquí, ya que Spring inyectará las propiedades
        // automáticamente
    }

    @Test
    public void testGetData() {
        // Configuración de las propiedades de configuración
        Map<String, String> mockData = Map.of("key1", "value1", "key2", "value2");

        // Setear las propiedades directamente en el objeto (simula cómo Spring lo
        // haría)
        userConfig.setData(mockData);

        // Llamar al método del getter
        Map<String, String> result = userConfig.getData();

        // Verificar que los valores sean correctos
        assertEquals(2, result.size());
        assertEquals("value1", result.get("key1"));
        assertEquals("value2", result.get("key2"));
    }
}
