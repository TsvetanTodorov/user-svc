package com.example.usersvc.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){

        Info applicationInfo = new Info()
                .title("Users REST API")
                .description("A REST API developed to manage user data.")
                .version("1.0");

        return new OpenAPI().info(applicationInfo);

    }

}
