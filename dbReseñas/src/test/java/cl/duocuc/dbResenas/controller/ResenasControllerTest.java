package cl.duocuc.dbResenas.controller;

import static org.mockito.ArgumentMatchers.any;
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

import cl.duocuc.dbResenas.Controller.ResenasController;
import cl.duocuc.dbResenas.Model.Resenas;
import cl.duocuc.dbResenas.Service.ResenasService;

@WebMvcTest(ResenasController.class)
public class ResenasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResenasService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarReseñas_Retorna200_CuandoHayDatos() throws Exception {
        when(service.listaReseñas()).thenReturn(Arrays.asList(new Resenas()));
        mockMvc.perform(get("/api/v1/resenas")).andExpect(status().isOk());
    }

    @Test
    void listarReseñas_Retorna204_CuandoEstaVacio() throws Exception {
        when(service.listaReseñas()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/v1/resenas")).andExpect(status().isNoContent());
    }

    @Test
    void obtenerResenaPorId_Retorna200_CuandoExiste() throws Exception {
        Resenas r = new Resenas(1, "Prod", 10, 5, "Buena");
        when(service.buscarPorId(1)).thenReturn(r);
        mockMvc.perform(get("/api/v1/resenas/1")).andExpect(status().isOk());
    }

    @Test
    void obtenerResenaPorId_Retorna404_CuandoNoExiste() throws Exception {
        when(service.buscarPorId(99)).thenThrow(new RuntimeException());
        mockMvc.perform(get("/api/v1/resenas/99")).andExpect(status().isNotFound());
    }

    @Test
    void guardarResena_Retorna200_Exitoso() throws Exception {
        Resenas r = new Resenas(null, "Prod", 10, 5, "Buena");
        when(service.guardarResenas(any())).thenReturn(r);

        mockMvc.perform(post("/api/v1/resenas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(r)))
                .andExpect(status().isOk());
    }

    @Test
    void guardarResena_Retorna500_CuandoFallaElServicio() throws Exception {
        // 1. Arrange: Preparamos datos válidos para la petición
        Resenas r = new Resenas(null, "Prod", 999, 5, "Buena");
    
        // Configuramos el mock para que lance una excepción (simulando que el producto no existe)
        when(service.guardarResenas(any(Resenas.class)))
            .thenThrow(new RuntimeException("Producto no encontrado"));

        // 2. Act & Assert: Verificamos que el servidor responde con 500
        mockMvc.perform(post("/api/v1/resenas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(r)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void eliminarResena_Retorna204_Exitoso() throws Exception {
        doNothing().when(service).eliminarResena(1);
        mockMvc.perform(delete("/api/v1/resenas/1")).andExpect(status().isNoContent());
    }

    @Test
    void eliminarResena_Retorna404_CuandoNoExiste() throws Exception {
        doThrow(new RuntimeException()).when(service).eliminarResena(99);
        mockMvc.perform(delete("/api/v1/resenas/99")).andExpect(status().isNotFound());
    }
}
