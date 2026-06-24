package cl.duocuc.dbReportes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DbReportesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbReportesApplication.class, args);
	}

}
