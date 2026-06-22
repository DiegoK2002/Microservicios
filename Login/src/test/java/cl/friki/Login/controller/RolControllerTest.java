package cl.friki.Login.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.friki.Login.model.Rol;
import cl.friki.Login.service.RolService;

@WebMvcTest(RolController.class)
public class RolControllerTest {

    @MockBean
    private RolService rolService;

    @Autowired
    private MockMvc llamadaFalsa;

    private Rol rolEjemplo;

    @BeforeEach
    void setUp() {
        rolEjemplo = new Rol();
        rolEjemplo.setId(1);
        rolEjemplo.setNombreRol("ADMIN");
    }

    @Test
    void buscarPorId_retorna200() throws Exception {

        when(rolService.buscarPorId(1)).thenReturn(rolEjemplo);

    
        llamadaFalsa.perform(get("/api/v1/roles/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombreRol").value("ADMIN"));
    }

    @Test
    void buscarPorId_retorna404() throws Exception {
    
        when(rolService.buscarPorId(99)).thenThrow(new RuntimeException("Rol no encontrado"));

    
        llamadaFalsa.perform(get("/api/v1/roles/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void eliminarRol_retorna204() throws Exception {
     
        doNothing().when(rolService).eliminarRol(1);

      
        llamadaFalsa.perform(delete("/api/v1/roles/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void eliminarRol_retorna404() throws Exception {
     
        doThrow(new RuntimeException("Rol no encontrado"))
            .when(rolService).eliminarRol(99);

        llamadaFalsa.perform(delete("/api/v1/roles/99"))
            .andExpect(status().isNotFound());
    }
}