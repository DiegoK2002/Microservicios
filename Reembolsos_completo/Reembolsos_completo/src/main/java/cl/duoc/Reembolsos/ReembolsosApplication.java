package cl.duoc.Reembolsos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ReembolsosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReembolsosApplication.class, args);
	}

}
