package cl.duocuc.dbEnvio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DbEnvioApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbEnvioApplication.class, args);
	}

}
