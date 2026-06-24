package cl.friki.notificaciones;

import cl.friki.notificaciones.controller.NotificacionController;
import cl.friki.notificaciones.dto.NotificacionDTO;
import cl.friki.notificaciones.dto.NotificacionRequestDTO;
import cl.friki.notificaciones.service.NotificacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificacionController.class)
@DisplayName("Notificacion Controller Tests")
class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificacionService notificacionService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    private NotificacionDTO crearDTO(Long id, Long clienteId, String tipo) {
        NotificacionDTO dto = new NotificacionDTO();
        dto.setId(id);
        dto.setClienteId(clienteId);
        dto.setTipo(tipo);
        dto.setMensaje("Mensaje de prueba");
        dto.setFechaEnvio(LocalDateTime.now());
        dto.setLeida(false);
        return dto;
    }

    @Test
    void getAll_retorna200() throws Exception {
        when(notificacionService.findAll()).thenReturn(Arrays.asList(crearDTO(1L, 1L, "COMPRA")));

        mockMvc.perform(get("/api/v1/notificaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipo").value("COMPRA"));
    }

    @Test
    void getAll_listaVacia_retorna200() throws Exception {
        when(notificacionService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/notificaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getById_retorna200() throws Exception {
        when(notificacionService.findById(1L)).thenReturn(Optional.of(crearDTO(1L, 1L, "ENVIO")));

        mockMvc.perform(get("/api/v1/notificaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo").value("ENVIO"));
    }

    @Test
    void getById_noExiste_retorna404() throws Exception {
        when(notificacionService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/notificaciones/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getByClienteId_retorna200() throws Exception {
        when(notificacionService.findByClienteId(1L)).thenReturn(Arrays.asList(crearDTO(1L, 1L, "COMPRA")));

        mockMvc.perform(get("/api/v1/notificaciones/cliente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clienteId").value(1));
    }

    @Test
    void create_retorna201() throws Exception {
        NotificacionRequestDTO request = new NotificacionRequestDTO();
        request.setClienteId(1L);
        request.setTipo("COMPRA");
        request.setMensaje("Tu compra fue procesada");
        NotificacionDTO dto = crearDTO(1L, 1L, "COMPRA");
        when(notificacionService.create(any(NotificacionRequestDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/api/v1/notificaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipo").value("COMPRA"));
    }

    @Test
    void marcarComoLeida_retorna200() throws Exception {
        NotificacionDTO dto = crearDTO(1L, 1L, "COMPRA");
        dto.setLeida(true);
        when(notificacionService.marcarComoLeida(1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(put("/api/v1/notificaciones/1/leida"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.leida").value(true));
    }

    @Test
    void marcarComoLeida_noExiste_retorna404() throws Exception {
        when(notificacionService.marcarComoLeida(99L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/notificaciones/99/leida"))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_retorna204() throws Exception {
        when(notificacionService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/notificaciones/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_noExiste_retorna404() throws Exception {
        when(notificacionService.delete(99L)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/notificaciones/99"))
                .andExpect(status().isNotFound());
    }
}
