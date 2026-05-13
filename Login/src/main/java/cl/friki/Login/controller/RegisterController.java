package cl.friki.Login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.friki.Login.model.Register;
import cl.friki.Login.service.RegisterService;

@RestController
@RequestMapping("/api/v1/usuarios/register")
public class RegisterController {

    @Autowired
    private RegisterService service;    

    //crear usuario nuevo
    @PostMapping
    public ResponseEntity<Register> guardar(@RequestBody Register register){
        return ResponseEntity.ok(service.crearRegister(register));
    }
}
