package cl.friki.Login.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cl.friki.Login.dto.UsuarioLoginDTO;
import cl.friki.Login.model.Register;

@FeignClient(name = "Usuario", url = "http://localhost:8082")
public interface UsuarioClient {

    @PostMapping("/api/v1/usuarios")
    Register crearUsuario(@RequestBody Register register);

    @GetMapping("/api/v1/usuarios/buscar/{nombreUsuario}")
    UsuarioLoginDTO buscarPorNombre(@PathVariable(value = "nombreUsuario") String nombreUsuario);
}
