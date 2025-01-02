package org.example.votingapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class openAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {

        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("dev");

        Info information = new Info()
                .title("Voting API")
                .version("1.0")
                .description("Voting API description")
                ;


        return new OpenAPI().info(information).servers(List.of(server));
    }
}
