package cl.duoc.Reembolsos;

import cl.duoc.Reembolsos.Controller.ReembolsoController;
import cl.duoc.Reembolsos.Exception.ReembolsoNotFoundException;
import cl.duoc.Reembolsos.Model.Reembolso;
import cl.duoc.Reembolsos.Service.ReembolsoService;
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

@WebMvcTest(ReembolsoController.class)
@DisplayName("Reembolso Controller Tests")
class ReembolsoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReembolsoService reembolsoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Reembolso crearReembolso(Integer id, String nombre, Integer monto, String estado) {
        Reembolso r = new Reembolso();
        r.setId(id);
        r.setIdCompra(1);
        r.setNombreReembolso(nombre);
        r.setMonto(monto);
        r.setFecha("2024-01-15");
        r.setEstado(estado);
        r.setDescripcion("Descripción");
        return r;
    }

    @Test
    void listar_conDatos_retorna200() throws Exception {
        when(reembolsoService.listarTodos()).thenReturn(Arrays.asList(crearReembolso(1, "R1", 10000, "Pendiente")));

        mockMvc.perform(get("/api/v1/reembolsos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("Pendiente"));
    }

    @Test
    void listar_sinDatos_retorna204() throws Exception {
        when(reembolsoService.listarTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/reembolsos"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorId_retorna200() throws Exception {
        when(reembolsoService.buscarPorId(1)).thenReturn(crearReembolso(1, "R1", 10000, "Pendiente"));

        mockMvc.perform(get("/api/v1/reembolsos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreReembolso").value("R1"));
    }

    @Test
    void buscarPorId_noExiste_retorna404() throws Exception {
        when(reembolsoService.buscarPorId(999)).thenThrow(new ReembolsoNotFoundException(999));

        mockMvc.perform(get("/api/v1/reembolsos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarPorEstado_conDatos_retorna200() throws Exception {
        when(reembolsoService.buscarPorEstado("Aprobado")).thenReturn(
                Arrays.asList(crearReembolso(1, "R1", 20000, "Aprobado")));

        mockMvc.perform(get("/api/v1/reembolsos/estado/Aprobado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("Aprobado"));
    }

    @Test
    void buscarPorEstado_sinDatos_retorna204() throws Exception {
        when(reembolsoService.buscarPorEstado("Rechazado")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/reembolsos/estado/Rechazado"))
                .andExpect(status().isNoContent());
    }

    @Test
    void crear_valido_retorna201() throws Exception {
        Reembolso reembolso = crearReembolso(1, "Nuevo", 15000, "Pendiente");
        when(reembolsoService.crear(any(Reembolso.class))).thenReturn(reembolso);

        mockMvc.perform(post("/api/v1/reembolsos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reembolso)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreReembolso").value("Nuevo"));
    }

    @Test
    void actualizar_valido_retorna200() throws Exception {
        Reembolso reembolso = crearReembolso(1, "Actualizado", 25000, "Aprobado");
        when(reembolsoService.actualizar(eq(1), any(Reembolso.class))).thenReturn(reembolso);

        mockMvc.perform(put("/api/v1/reembolsos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reembolso)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("Aprobado"));
    }

    @Test
    void eliminar_retorna204() throws Exception {
        doNothing().when(reembolsoService).eliminar(1);

        mockMvc.perform(delete("/api/v1/reembolsos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerDTO_retorna200() throws Exception {
        Reembolso reembolso = crearReembolso(1, "R1", 10000, "Pendiente");
        cl.duoc.Reembolsos.DTO.ReembolsoDTO dto = new cl.duoc.Reembolsos.DTO.ReembolsoDTO(
                1, "R1", 10000, "2024-01-15", "Pendiente", "Descripción");
        when(reembolsoService.buscarPorId(1)).thenReturn(reembolso);
        when(reembolsoService.toDTO(reembolso)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/reembolsos/dto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreReembolso").value("R1"));
    }
}
