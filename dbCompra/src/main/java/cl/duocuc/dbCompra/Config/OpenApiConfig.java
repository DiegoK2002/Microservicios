package cl.duocuc.dbCompra.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI compraOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio de Compras")
                        .description("API REST para la gestión de compras")
                        .version("v3"));
    }
}
