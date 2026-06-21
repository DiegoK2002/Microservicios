package cl.duocuc.dbCompra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DbCompraApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbCompraApplication.class, args);
	}

}
