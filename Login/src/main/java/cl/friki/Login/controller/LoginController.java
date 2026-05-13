package cl.friki.Login.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.friki.Login.model.Register;
import cl.friki.Login.service.LoginService;

@RestController
@RequestMapping("/api/v1/usuarios/login")
public class LoginController {

    @Autowired
    private LoginService service;
    
    @GetMapping
    public ResponseEntity<Register> buscarPorId(@RequestBody String userName, String password){
        return service.usuarioLogin(userName, password);
        
    }

}
