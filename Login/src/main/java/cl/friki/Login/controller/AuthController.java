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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Autentificación", description = "Operación sobre crear cuentas e inicios de sesión")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Crea un nuevo usuario dentro del sistema")
    public ResponseEntity<Register> register(@RequestBody Register register) {
        try {
            return ResponseEntity.ok(authService.registrar(register));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Autentifica que el usuario ha sido creado")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (Exception e) {
            System.err.println("Error en el proceso de login: " + e.getMessage());
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}