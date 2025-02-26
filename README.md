##  Instrucciones de Uso
- Para limpiar, instalar y ejecutar la aplicación sin pruebas:

  ```bash
  ./mvn clean install -DskipTests spring-boot:run
  ```

- Para ejecutar los tests:

  ```bash
  mvn test
  ```

## Crear un Usuario

- Para crear un usuario, usa el siguiente JSON:

```json
{
  "email": "sole.faundez.c@gmail.com",
  "name": "Soledad",
  "password": "123456",
  "phones": [
    {
      "citycode": "111",
      "contrycode": "15222",
      "number": "5212222"
    },
    {
      "citycode": "111",
      "contrycode": "15222",
      "number": "5554400"
    }
  ]
}
```

## Revisar la API

- Para acceder a la documentación de la API, visita:  
  [Swagger UI](http://localhost:8080/swagger-ui/index.html#/user-controller)

- Para acceder a la consola de H2, visita:  
  [H2 Console](http://localhost:8080/h2-console)  
  Configura la URL de JDBC como:  
  `jdbc:h2:mem:userdb`
```

