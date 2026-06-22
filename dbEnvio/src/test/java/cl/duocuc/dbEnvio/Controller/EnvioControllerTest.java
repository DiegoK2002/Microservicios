package cl.duocuc.dbEnvio.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.duocuc.dbEnvio.Client.CompraClient;
import cl.duocuc.dbEnvio.Dto.CompraDTO;
import cl.duocuc.dbEnvio.Model.Envio;
import cl.duocuc.dbEnvio.Model.Repartidor;
import cl.duocuc.dbEnvio.Service.EnvioService;

@WebMvcTest(EnvioController.class)
public class EnvioControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private EnvioService service;
    @MockitoBean private CompraClient compraClient;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void listarEnvios_Retorna200_CuandoHayEnvios() throws Exception {
        // 1. Arrange: Simulamos que el servicio retorna una lista con envíos
        List<Envio> listaMock = Arrays.asList(new Envio(1, 10, null), new Envio(2, 20, null));
        when(service.listaEnvios()).thenReturn(listaMock);

        // 2. Act & Assert: Verificamos el estado 200 y que el cuerpo de la respuesta no esté vacío
        mockMvc.perform(get("/api/v1/envios"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void listarEnvios_Retorna204_CuandoNoHayEnvios() throws Exception {
        // 1. Arrange: Simulamos que el servicio retorna una lista vacía
        when(service.listaEnvios()).thenReturn(Collections.emptyList());

        // 2. Act & Assert: Verificamos el estado 204
        mockMvc.perform(get("/api/v1/envios"))
               .andExpect(status().isNoContent());
    }

    @Test
    void buscarDTO_Retorna200_CuandoExisteEnvioYCompra() throws Exception {
        // 1. Arrange
        Integer id = 1;
        Envio envioMock = new Envio(id, 100, new Repartidor(1, "Juan Perez"));
        CompraDTO compraMock = new CompraDTO(); // Puedes rellenar datos si lo necesitas
    
        when(service.buscarPorId(id)).thenReturn(envioMock);
        when(compraClient.obtenerCompraPorId(anyInt())).thenReturn(compraMock);

        // 2. Act & Assert
        mockMvc.perform(get("/api/v1/envios/id/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(id))
               .andExpect(jsonPath("$.nombreRepartidor").value("Juan Perez"));
    
        verify(service, times(1)).buscarPorId(id);
        verify(compraClient, times(1)).obtenerCompraPorId(anyInt());
    }

    @Test
    void buscarDTO_Retorna404_CuandoOcurreExcepcion() throws Exception {
        // 1. Arrange: Simulamos que el servicio falla al buscar
        when(service.buscarPorId(anyInt())).thenThrow(new RuntimeException("No encontrado"));

        // 2. Act & Assert
        mockMvc.perform(get("/api/v1/envios/id/99"))
               .andExpect(status().isNotFound());
    }

    @Test
    void guardarEnvio_Retorna200_Exitoso() throws Exception {
        Envio envio = new Envio(1, 1, null);
        when(service.guardarEnvio(any())).thenReturn(envio);

        mockMvc.perform(post("/api/v1/envios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(envio)))
                .andExpect(status().isOk());
    }

    @Test
    void guardarEnvio_Retorna400_CuandoFalla() throws Exception {
        when(service.guardarEnvio(any())).thenThrow(new RuntimeException());
        
        mockMvc.perform(post("/api/v1/envios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }
}
