package cl.friki.Login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.friki.Login.dto.LoginRequest;
import cl.friki.Login.model.Register;
import cl.friki.Login.service.AuthService;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Register> register(@RequestBody Register register) {
        try {
            return ResponseEntity.ok(authService.registrar(register));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (Exception e) {
            System.err.println("Error en el proceso de login: " + e.getMessage());
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}