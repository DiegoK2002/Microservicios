package cl.friki.Usuario.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.friki.Usuario.client.RolClient;
import cl.friki.Usuario.model.Direccion;
import cl.friki.Usuario.model.Usuario;
import cl.friki.Usuario.service.UsuarioService;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private RolClient rolClient;

    @Autowired
    private MockMvc llamadaFalsa;

    private Usuario usuarioEjemplo;

    @BeforeEach
    void setUp() {
        usuarioEjemplo = new Usuario();
        usuarioEjemplo.setId(1);
        usuarioEjemplo.setNombreUsuario("juan123");
        usuarioEjemplo.setCorreo("juan@gmail.com");
        usuarioEjemplo.setPassword("1234");
        usuarioEjemplo.setDireccion(new Direccion(1, "123", "Calle Falsa", "Santiago", "Metropolitana"));
        usuarioEjemplo.setIdRol(1);
    }

    @Test
    void buscarPorId_retorna200() throws Exception {
  
        when(usuarioService.buscarPorId(1)).thenReturn(usuarioEjemplo);

        llamadaFalsa.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreUsuario").value("juan123"));
    }

    @Test
    void buscarPorId_retorna404() throws Exception {
     
        when(usuarioService.buscarPorId(99)).thenThrow(new RuntimeException("no se encontró a ese usuario"));

        llamadaFalsa.perform(get("/api/v1/usuarios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminar_retorna204() throws Exception {
   
        doNothing().when(usuarioService).eliminarUsuario(1);

        llamadaFalsa.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_retorna404() throws Exception {

        doThrow(new RuntimeException("Usuario no encontrado"))
                .when(usuarioService).eliminarUsuario(99);

        llamadaFalsa.perform(delete("/api/v1/usuarios/99"))
                .andExpect(status().isNotFound());
    }
}