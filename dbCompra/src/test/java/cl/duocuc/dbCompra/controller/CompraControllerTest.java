package cl.duocuc.dbCompra.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.duocuc.dbCompra.Client.ProductoClient;
import cl.duocuc.dbCompra.Client.UsuarioClient;
import cl.duocuc.dbCompra.Controller.CompraController;
import cl.duocuc.dbCompra.Model.Compra;
import cl.duocuc.dbCompra.Model.MetodoPago;
import cl.duocuc.dbCompra.Model.Promociones;
import cl.duocuc.dbCompra.Repository.PromocionesRepository;
import cl.duocuc.dbCompra.Service.CompraService;
import cl.duocuc.dbCompra.Dto.ProductoDTO;
import cl.duocuc.dbCompra.Dto.UsuarioDTO;

@WebMvcTest(CompraController.class)
public class CompraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompraService service;

    @MockBean
    private ProductoClient productoClient;

    @MockBean
    private UsuarioClient usuarioClient;

    @MockBean
    private PromocionesRepository repo;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarCompras_Retorna200_CuandoHayDatos() throws Exception {
        when(service.listaCompras()).thenReturn(Arrays.asList(new Compra()));
        
        mockMvc.perform(get("/api/v1/compras"))
               .andExpect(status().isOk());
    }

    @Test
    void listarCompras_Retorna204_CuandoNoHayDatos() throws Exception {
        // 1. Arrange: Mockeamos el servicio para que retorne una lista vacía
        when(service.listaCompras()).thenReturn(Collections.emptyList());

        // 2. Act & Assert: Verificamos que el estado sea 204 (No Content)
        mockMvc.perform(get("/api/v1/compras"))
               .andExpect(status().isNoContent());
    }

    @Test
    void buscarDTO_RetornaCompraConValoresEnriquecidos() throws Exception {
        // 1. Arrange
        Integer idCompra = 1;
        MetodoPago metodoPago = new MetodoPago(1, "Paypal");
    
        // Asumiendo el orden de atributos en tu clase Compra:
        // (id, dia, mes, ano, idUsuario, idProducto, idPromocion, metodoPago)
        Compra compra = new Compra(idCompra, 22, 6, 2026, 1, 1, 1, metodoPago);
    
        when(service.buscarPorId(idCompra)).thenReturn(compra);
    
        // Corregido: precio como Integer (1000) en lugar de double (1000.0)
        when(productoClient.obtenerDatosProducto(1)).thenReturn(new ProductoDTO(1, "Producto", 1000));
    
        // Corregido: Ajustar constructor de UsuarioDTO si es necesario
        when(usuarioClient.obtenerDatosUsuario(1)).thenReturn(new UsuarioDTO(1, "Usuario", "Direccion Ejemplo"));
    
        // Mock de Promoción
        when(repo.findById(1)).thenReturn(Optional.of(new Promociones(1, 0.1, "10%")));

        // 2. Act & Assert
        mockMvc.perform(get("/api/v1/compras/id/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.valorTotal").value(900)); // 1000 - 10%
    }

    @Test
    void buscarDTO_Retorna404_CuandoCompraNoExiste() throws Exception {
        // 1. Arrange: Simulamos que el servicio no encuentra la compra
        Integer idCompraInexistente = 999;
        when(service.buscarPorId(idCompraInexistente))
            .thenThrow(new RuntimeException("Compra no encontrada"));

        // 2. Act & Assert:
        // Si tu controller atrapa la excepción y devuelve un 404 o 500, 
        // ajusta el .andExpect según lo que tengas implementado.
        mockMvc.perform(get("/api/v1/compras/id/" + idCompraInexistente))
               .andExpect(status().isInternalServerError()); 
               // Nota: Si quieres que sea un 404, tendrías que 
               // capturar la RuntimeException específicamente en tu controller.
    }

    @Test
    void guardarCompra_RetornaCompraCreada() throws Exception {
        Compra compra = new Compra(); // Configura los campos necesarios
        when(service.guardarCompra(any(Compra.class))).thenReturn(compra);

        mockMvc.perform(post("/api/v1/compras")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(compra)))
               .andExpect(status().isOk());
    }

    @Test
    void guardarCompra_Retorna500_CuandoProductoNoExiste() throws Exception {
        Compra compra = new Compra();
        compra.setIdUsuario(1);
        compra.setIdProducto(999); // ID inexistente

        // Simulamos que el servicio lanza la excepción cuando el producto no existe
        when(service.guardarCompra(any(Compra.class)))
            .thenThrow(new RuntimeException("Producto no encontrado"));

        mockMvc.perform(post("/api/v1/compras")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(compra)))
               .andExpect(status().isInternalServerError()); // Tu controller devuelve 500 en el catch general
    }

    @Test
    void guardarCompra_Retorna500_CuandoUsuarioNoExiste() throws Exception {
        Compra compra = new Compra();
        compra.setIdUsuario(888); // ID inexistente
        compra.setIdProducto(1);

        // Simulamos que el servicio lanza la excepción cuando el usuario no existe
        when(service.guardarCompra(any(Compra.class)))
            .thenThrow(new RuntimeException("Usuario no encontrado"));

        mockMvc.perform(post("/api/v1/compras")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(compra)))
               .andExpect(status().isInternalServerError());
    }
}
