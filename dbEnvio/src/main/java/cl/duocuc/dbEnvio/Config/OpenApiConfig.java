package cl.duocuc.dbEnvio.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI enviosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio de Envios")
                        .description("API REST para la gestión de envios")
                        .version("v3"));
    }
}
