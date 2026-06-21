package cl.friki.Usuario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import cl.friki.Usuario.dto.RolDTO;

@FeignClient(name = "Login", url = "http://localhost:8081")
public interface RolClient {

    @GetMapping("/api/v1/roles/dto/{id}") 
    RolDTO obtenerRol(@PathVariable("id") Integer id);
}
