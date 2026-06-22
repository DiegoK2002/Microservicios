package cl.duoc.Remuneraciones;

import cl.duoc.Remuneraciones.controller.RemuneracionController;
import cl.duoc.Remuneraciones.model.Remuneracion;
import cl.duoc.Remuneraciones.service.RemuneracionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RemuneracionController.class)
@DisplayName("Remuneracion Controller Tests")
class RemuneracionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RemuneracionService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Remuneracion crearRemuneracion(Integer id, String empleado, String estado) {
        Remuneracion r = new Remuneracion();
        r.setId(id);
        r.setNombreEmpleado(empleado);
        r.setSalarioBase(800000.0);
        r.setBonificacion(50000.0);
        r.setDescuentos(20000.0);
        r.setFechaPago("2024-01-31");
        r.setEstado(estado);
        r.setDescripcion("Remuneración de prueba");
        return r;
    }

    @Test
    void listar_retorna200() throws Exception {
        when(service.listarTodas()).thenReturn(Arrays.asList(crearRemuneracion(1, "Juan", "PENDIENTE")));

        mockMvc.perform(get("/api/v1/remuneraciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreEmpleado").value("Juan"));
    }

    @Test
    void listar_listaVacia_retorna204() throws Exception {
        when(service.listarTodas()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/remuneraciones"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorId_retorna200() throws Exception {
        when(service.buscarPorId(1)).thenReturn(crearRemuneracion(1, "Ana", "PAGADO"));

        mockMvc.perform(get("/api/v1/remuneraciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("PAGADO"));
    }

    @Test
    void buscarPorId_noExiste_retorna404() throws Exception {
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("no encontrada"));

        mockMvc.perform(get("/api/v1/remuneraciones/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crear_retorna201() throws Exception {
        Remuneracion entrada = crearRemuneracion(null, "Pedro", "PENDIENTE");
        Remuneracion creada = crearRemuneracion(1, "Pedro", "PENDIENTE");
        when(service.crear(any(Remuneracion.class))).thenReturn(creada);

        mockMvc.perform(post("/api/v1/remuneraciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreEmpleado").value("Pedro"));
    }

    @Test
    void pagar_retorna200() throws Exception {
        Remuneracion pagada = crearRemuneracion(1, "Ana", "PAGADO");
        when(service.pagar(1)).thenReturn(pagada);

        mockMvc.perform(put("/api/v1/remuneraciones/1/pagar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("PAGADO"));
    }

    @Test
    void eliminar_retorna204() throws Exception {
        doNothing().when(service).eliminar(eq(1));

        mockMvc.perform(delete("/api/v1/remuneraciones/1"))
                .andExpect(status().isNoContent());
    }
}
