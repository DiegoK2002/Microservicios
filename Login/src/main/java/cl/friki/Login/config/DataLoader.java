package cl.friki.Login.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.friki.Login.model.Rol;
import cl.friki.Login.repository.RolRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initRoles(RolRepository rolRepository) {
        return args -> {
            if (rolRepository.count() == 0) {
                rolRepository.save(new Rol(null, "ADMIN"));
                rolRepository.save(new Rol(null, "VENDEDOR"));
                rolRepository.save(new Rol(null, "CLIENTE"));
            }
        };
    }
}
