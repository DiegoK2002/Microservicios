package cl.duoc.Notificaciones.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.duoc.Notificaciones.Controller.NotificacionController;
import cl.duoc.Notificaciones.DTO.NotificacionDTO;
import cl.duoc.Notificaciones.Model.Notificacion;
import cl.duoc.Notificaciones.Service.NotificacionService;

@WebMvcTest(NotificacionController.class)
public class NotificacionesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificacionService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listar_Retorna200_CuandoHayDatos() throws Exception {
        when(service.listarTodas()).thenReturn(Arrays.asList(new Notificacion()));
        mockMvc.perform(get("/api/v1/notificaciones")).andExpect(status().isOk());
    }

    @Test
    void listar_Retorna204_CuandoNoHayDatos() throws Exception {
        // Arrange: Configuramos el mock para que retorne una lista vacía
        when(service.listarTodas()).thenReturn(Collections.emptyList());

        // Act & Assert: Verificamos que el estado sea 204 (No Content)
        mockMvc.perform(get("/api/v1/notificaciones"))
               .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorId_Retorna200_CuandoExiste() throws Exception {
        Notificacion n = new Notificacion(1, "User", "Msg", "F", "E");
        when(service.buscarPorId(1)).thenReturn(n);
        mockMvc.perform(get("/api/v1/notificaciones/1")).andExpect(status().isOk());
    }

    @Test
    void buscarPorId_Retorna404_CuandoNoExiste() throws Exception {
        // Arrange: Configuramos el mock para que lance una excepción al buscar por ID
        Integer idInexistente = 99;
        when(service.buscarPorId(idInexistente))
            .thenThrow(new RuntimeException("Notificación no encontrada"));

        // Act & Assert: Verificamos que el controlador devuelve 404
        mockMvc.perform(get("/api/v1/notificaciones/" + idInexistente))
               .andExpect(status().isNotFound());
    }

    @Test
    void obtenerDTO_Retorna200_CuandoExiste() throws Exception {
        Notificacion n = new Notificacion(1, "User", "Msg", "F", "E");
        when(service.buscarPorId(1)).thenReturn(n);
        when(service.toDTO(any())).thenReturn(new NotificacionDTO(1, "User", "Msg", "F", "E"));
        
        mockMvc.perform(get("/api/v1/notificaciones/dto/1")).andExpect(status().isOk());
    }

    @Test
    void crear_Retorna201_CuandoEsValido() throws Exception {
        Notificacion n = new Notificacion(1, "User", "Msg", "F", "E");
        when(service.crear(any())).thenReturn(n);

        mockMvc.perform(post("/api/v1/notificaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(n)))
                .andExpect(status().isCreated());
    }

    @Test
    void crear_Retorna400_CuandoDatosSonInvalidos() throws Exception {
        // Arrange: Preparamos un objeto inválido
        Notificacion n = new Notificacion(null, "", "", "", "");
    
        // Configuramos el mock para que lance una excepción (simulando la validación del servicio)
        when(service.crear(any(Notificacion.class)))
            .thenThrow(new RuntimeException("Destinatario o mensaje obligatorio"));

        // Act & Assert: Verificamos que el controlador devuelve 400 Bad Request
        mockMvc.perform(post("/api/v1/notificaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(n)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void actualizar_Retorna200_CuandoEsExitoso() throws Exception {
        // 1. Arrange: Preparamos datos válidos
        Integer id = 1;
        Notificacion notificacion = new Notificacion(id, "Usuario", "Mensaje", "22-06-2026", "Enviado");
    
        // Configuramos el mock para que retorne el objeto exitosamente al ser llamado
        when(service.actualizar(eq(id), any(Notificacion.class))).thenReturn(notificacion);

        // 2. Act & Assert: Realizamos el PUT y verificamos el estado 200 (OK)
        mockMvc.perform(put("/api/v1/notificaciones/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(notificacion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.destinatario").value("Usuario")); // Verificamos contenido si es necesario
    }

    @Test
    void actualizar_Retorna404_CuandoNoExiste() throws Exception {
        // Simulamos error al no encontrar ID
        when(service.actualizar(eq(99), any())).thenThrow(new RuntimeException());
        
        mockMvc.perform(put("/api/v1/notificaciones/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void cambiarEstado_Retorna200_Exitoso() throws Exception {
        when(service.cambiarEstado(1, "Enviada")).thenReturn(new Notificacion());
        mockMvc.perform(patch("/api/v1/notificaciones/1/estado/Enviada"))
               .andExpect(status().isOk());
    }

    @Test
    void cambiarEstado_Retorna404_CuandoNoExiste() throws Exception {
        // Arrange: Configuramos el mock para lanzar una excepción si el ID no es válido
        Integer idInexistente = 99;
        when(service.cambiarEstado(eq(idInexistente), anyString()))
            .thenThrow(new RuntimeException("Notificación no encontrada"));

        // Act & Assert: Verificamos que el controlador devuelve 404 Not Found
        mockMvc.perform(patch("/api/v1/notificaciones/" + idInexistente + "/estado/Enviada"))
               .andExpect(status().isNotFound());
    }

    @Test
    void eliminar_Retorna204_Exitoso() throws Exception {
        doNothing().when(service).eliminar(1);
        mockMvc.perform(delete("/api/v1/notificaciones/1")).andExpect(status().isNoContent());
    }

    @Test
    void eliminar_Retorna404_CuandoNoExiste() throws Exception {
        // Arrange: Simulamos que al intentar eliminar, el servicio lanza una excepción
        // porque la notificación no existe
        Integer idInexistente = 99;
        doThrow(new RuntimeException("Notificación no encontrada"))
            .when(service).eliminar(idInexistente);

        // Act & Assert: Verificamos que el controlador devuelve 404
        mockMvc.perform(delete("/api/v1/notificaciones/" + idInexistente))
               .andExpect(status().isNotFound());
    }
}
