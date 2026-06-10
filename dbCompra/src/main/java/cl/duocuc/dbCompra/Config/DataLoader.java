package cl.duocuc.dbCompra.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duocuc.dbCompra.Model.MetodoPago;
import cl.duocuc.dbCompra.Model.Promociones;
import cl.duocuc.dbCompra.Repository.MetodoPagoRepository;
import cl.duocuc.dbCompra.Repository.PromocionesRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initPromociones(PromocionesRepository repo1){
        return args -> {
            if(repo1.count() == 0){
                repo1.save(new Promociones(null, 0.00));
                repo1.save(new Promociones(null, 0.10));
                repo1.save(new Promociones(null, 0.20));
                repo1.save(new Promociones(null, 0.30));
                repo1.save(new Promociones(null, 0.40));
                repo1.save(new Promociones(null, 0.50));
                repo1.save(new Promociones(null, 0.60));
                repo1.save(new Promociones(null, 0.70));
                repo1.save(new Promociones(null, 0.80));
                repo1.save(new Promociones(null, 0.90));
                repo1.save(new Promociones(null, 1.00));
            }
        };
    }
    @Bean
    CommandLineRunner initMetodoPago(MetodoPagoRepository repo2){
        return args -> {
            if(repo2.count() == 0){
                repo2.save(new MetodoPago(null, "Débito"));
                repo2.save(new MetodoPago(null, "Crédito"));
                repo2.save(new MetodoPago(null, "Prepago"));
                repo2.save(new MetodoPago(null, "PayPal"));
            }
        };
    }
}
