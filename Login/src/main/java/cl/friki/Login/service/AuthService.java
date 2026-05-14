package cl.friki.Login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.friki.Login.dto.LoginRequest;
import cl.friki.Login.dto.LoginResponse;
import cl.friki.Login.model.Register;
import cl.friki.Login.repository.RegisterRepository;

@Service
public class AuthService {

    @Autowired
    private RegisterRepository registerRepository;

    // Guarda el usuario nuevo
    public Register registrar(Register register) {
        // Verificar si el userName ya existe
        if (registerRepository.findByUserName(register.getUserName()).isPresent()) {
            throw new RuntimeException("Ese nombre de usuario ya está en uso");
        }
        // Verificar si el correo ya existe
        if (registerRepository.findByCorreo(register.getCorreo()).isPresent()) {
            throw new RuntimeException("Ese correo ya está registrado");
        }
        return registerRepository.save(register);
    }

    // Busca el usuario y compara la password
    public LoginResponse login(LoginRequest request) {
        // Buscar usuario por userName
        Register usuario = registerRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return new LoginResponse("Inicio de sesión exitoso", usuario.getUserName(), usuario.getRol().getNombreRol());
    }
}
