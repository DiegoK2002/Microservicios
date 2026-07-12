package cl.duocuc.dbResenas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI resenasOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio de Reseñas")
                        .description("API REST para la gestión de reseñas")
                        .version("v3"));
    }
}
