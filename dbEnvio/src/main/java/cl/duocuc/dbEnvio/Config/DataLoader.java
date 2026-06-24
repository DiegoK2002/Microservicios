package cl.duocuc.dbEnvio.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duocuc.dbEnvio.Model.Repartidor;
import cl.duocuc.dbEnvio.Repository.RepartidorRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initRepartidores(RepartidorRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Repartidor(null, "Carlos García"));
                repo.save(new Repartidor(null, "María López"));
                repo.save(new Repartidor(null, "Juan Pérez"));
            }
        };
    }
}