package com.example.effectivemobiletest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Effective_Mobile API",
                version = "v1"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080/", description = "Local server"
                )
        }
)
public class EffectiveMobileTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(EffectiveMobileTestApplication.class, args);
    }

}
