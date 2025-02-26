package com.bci.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import java.util.function.Predicate;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket todoApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("REST-APIs")
                .apiInfo(apiInfo())
                .select()
                .paths(postPaths()::test)
                .build();
    }

    private Predicate<String> postPaths() {
        return regex("/*.*"); // EFC: Se quita el aply que estaba acá, según: le pongo *.* para que tome todos
                              // los controllers
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Test Apis")
                .description("Test Apis h2")
                .contact(new Contact("Soledad Faundez", "https://github.com/soledadfaundez",
                        "sole.faundez.c@gmail.com"))
                .version("1.0")
                .build();
    }
}
