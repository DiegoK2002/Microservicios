package cl.duocuc.dbEnvio.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duocuc.dbEnvio.Dto.UsuarioDTO;

@FeignClient(name = "Usuario", url = "http://localhost:8082")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/dto/{id}")
    UsuarioDTO obtenerDatosUsuario(@PathVariable("id") Integer id);
}