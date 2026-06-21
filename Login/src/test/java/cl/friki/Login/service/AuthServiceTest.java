package cl.friki.Login.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
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
    private AuthService authService;

    private Register registerEjemplo;

    @BeforeEach
    void setUp() {
        DireccionRegisterDTO dir = new DireccionRegisterDTO();
        dir.setNumVivienda("123");
        dir.setCalle("Calle Falsa");
        dir.setCiudad("Santiago");
        dir.setRegion("Metropolitana");

        registerEjemplo = new Register();
        registerEjemplo.setNombreUsuario("juan123");
        registerEjemplo.setCorreo("juan@gmail.com");
        registerEjemplo.setPassword("1234");
        registerEjemplo.setDireccion(dir);
        registerEjemplo.setIdRol(1);
    }

    // registrar
    @Test
    void registrar_llamaAUsuarioClientYRetornaRegister() {
        // ARRANGE
        when(usuarioClient.crearUsuario(registerEjemplo)).thenReturn(registerEjemplo);

       
        Register resultado = authService.registrar(registerEjemplo);

   
        assertEquals("juan123", resultado.getNombreUsuario());
        verify(usuarioClient, times(1)).crearUsuario(registerEjemplo);
    }

    // login
    @Test
    void login_credencialesCorrectas_retornaLoginResponse() {
        // ARRANGE
        LoginRequest request = new LoginRequest("juan123", "1234");
        UsuarioLoginDTO usuarioDTO = new UsuarioLoginDTO("juan123", "1234");
        when(usuarioClient.buscarPorNombre("juan123")).thenReturn(usuarioDTO);

   
        LoginResponse resultado = authService.login(request);


        assertEquals("Login exitoso", resultado.getMensaje());
        assertEquals("juan123", resultado.getNombreUsuario());
        assertEquals("CLIENTE", resultado.getRol());
    }

    @Test
    void login_passwordIncorrecta_lanzaExcepcion() {
    
        LoginRequest request = new LoginRequest("juan123", "incorrecta");
        UsuarioLoginDTO usuarioDTO = new UsuarioLoginDTO("juan123", "1234");
        when(usuarioClient.buscarPorNombre("juan123")).thenReturn(usuarioDTO);

     
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });

        assertEquals("Contraseña incorrecta", error.getMessage());
    }

    @Test
    void login_usuarioNoEncontrado_lanzaExcepcion() {
    
        LoginRequest request = new LoginRequest("inexistente", "1234");
        when(usuarioClient.buscarPorNombre("inexistente")).thenReturn(null);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });

        assertEquals("Usuario no encontrado", error.getMessage());
    }
}