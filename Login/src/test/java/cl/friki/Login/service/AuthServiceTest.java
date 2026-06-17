package cl.friki.Login.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.friki.Login.client.UsuarioClient;
import cl.friki.Login.dto.DireccionRegisterDTO;
import cl.friki.Login.dto.LoginRequest;
import cl.friki.Login.dto.LoginResponse;
import cl.friki.Login.dto.UsuarioLoginDTO;
import cl.friki.Login.model.Register;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private AuthService service;

    private Register crearRegister() {
        DireccionRegisterDTO dir = new DireccionRegisterDTO();
        dir.setNumVivienda("123");
        dir.setCalle("Calle Falsa");
        dir.setCiudad("Santiago");
        dir.setRegion("Metropolitana");
        return new Register("juan123", "juan@gmail.com", "1234", dir, 1);
    }

    // registrar
    @Test
    void registrar_llamaAUsuarioClientYRetornaRegister() {
        Register register = crearRegister();
        when(usuarioClient.crearUsuario(register)).thenReturn(register);

        Register resultado = service.registrar(register);

        assertNotNull(resultado);
        assertEquals("juan123", resultado.getNombreUsuario());
        verify(usuarioClient, times(1)).crearUsuario(register);
    }

    // login
    @Test
    void login_credencialesCorrectas_retornaLoginResponse() {
        LoginRequest request = new LoginRequest("juan123", "1234");
        UsuarioLoginDTO usuarioDTO = new UsuarioLoginDTO("juan123", "1234");
        when(usuarioClient.buscarPorNombre("juan123")).thenReturn(usuarioDTO);

        LoginResponse resultado = service.login(request);

        assertNotNull(resultado);
        assertEquals("Login exitoso", resultado.getMensaje());
        assertEquals("juan123", resultado.getNombreUsuario());
        assertEquals("CLIENTE", resultado.getRol());
    }

    @Test
    void login_passwordIncorrecta_lanzaExcepcion() {
        LoginRequest request = new LoginRequest("juan123", "incorrecta");
        UsuarioLoginDTO usuarioDTO = new UsuarioLoginDTO("juan123", "1234");
        when(usuarioClient.buscarPorNombre("juan123")).thenReturn(usuarioDTO);

        assertThrows(RuntimeException.class, () -> service.login(request));
    }

    @Test
    void login_usuarioNoEncontrado_lanzaExcepcion() {
        LoginRequest request = new LoginRequest("inexistente", "1234");
        when(usuarioClient.buscarPorNombre("inexistente")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> service.login(request));
    }
}