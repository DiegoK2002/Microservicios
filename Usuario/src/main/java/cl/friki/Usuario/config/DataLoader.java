package cl.friki.Usuario.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import cl.friki.Usuario.model.Direccion;
import cl.friki.Usuario.repository.DireccionRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initData(DireccionRepository repo){
        return args -> {

            if(repo.count() == 0){ 
                repo.save(new Direccion(null,"1234","a","b","c"));
            }
        };
    }
}

