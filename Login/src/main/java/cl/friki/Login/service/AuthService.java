package cl.friki.Login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.friki.Login.client.UsuarioClient;
import cl.friki.Login.dto.LoginRequest;
import cl.friki.Login.dto.LoginResponse;
import cl.friki.Login.dto.UsuarioLoginDTO;
import cl.friki.Login.model.Register;

@Service
public class AuthService {

    @Autowired
    private UsuarioClient usuarioClient;

    public Register registrar(Register register) {
        return usuarioClient.crearUsuario(register); 
    }

   public LoginResponse login(LoginRequest request) {
    UsuarioLoginDTO usuario = usuarioClient.buscarPorNombre(request.getNombreUsuario());

    if (usuario == null) {
        throw new RuntimeException("Usuario no encontrado");
    }

    if (!usuario.getPassword().equals(request.getPassword())) {
        throw new RuntimeException("Contraseña incorrecta");
    }

    return new LoginResponse("Login exitoso", usuario.getNombreUsuario(), "CLIENTE");
}


}
