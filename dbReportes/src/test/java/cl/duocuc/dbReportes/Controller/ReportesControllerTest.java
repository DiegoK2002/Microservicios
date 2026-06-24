package cl.duocuc.dbReportes.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

import cl.duocuc.dbReportes.Client.CompraClient;
import cl.duocuc.dbReportes.Dto.CompraDTO;
import cl.duocuc.dbReportes.Model.Reportes;
import cl.duocuc.dbReportes.Service.ReportesService;

@WebMvcTest(ReportesController.class)
public class ReportesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportesService service;

    @MockBean
    private CompraClient compraClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarReportes_Retorna200_ConDatos() throws Exception {
        when(service.listaReporte()).thenReturn(Arrays.asList(new Reportes(1, 100)));

        mockMvc.perform(get("/api/v1/reportes"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void buscarPorAño_RetornaLista_ConCompraYReporte() throws Exception {
        // Arrange
        CompraDTO compra = new CompraDTO();
        compra.setId(100);
        Reportes reporte = new Reportes(1, 100);

        when(compraClient.obtenerCompraPorAño(2025)).thenReturn(Collections.singletonList(compra));
        when(service.buscarPorIdCompra(100)).thenReturn(reporte);

        // Act & Assert
        mockMvc.perform(get("/api/v1/reportes/año/2025"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].compraDTO.id").value(100));
    }

    @Test
    void guardarReporte_Retorna200_AlGuardar() throws Exception {
        Reportes nuevoReporte = new Reportes(null, 500);
        Reportes guardado = new Reportes(1, 500);

        when(service.guardarReportes(any(Reportes.class))).thenReturn(guardado);

        mockMvc.perform(post("/api/v1/reportes")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(nuevoReporte)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void buscarDTO_ErrorServidor_Retorna500() throws Exception {
        when(service.buscarPorId(anyInt())).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/api/v1/reportes/id/1"))
               .andExpect(status().isInternalServerError());
    }
}
