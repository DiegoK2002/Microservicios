package cl.duocuc.dbReportes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI reportesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio de Reportes")
                        .description("API REST para la gestión de reportes")
                        .version("v3"));
    }
}
