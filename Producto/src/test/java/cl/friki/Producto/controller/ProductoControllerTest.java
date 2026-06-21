package cl.friki.Producto.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.friki.Producto.model.Producto;
import cl.friki.Producto.service.ProductoService;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @MockBean
    private ProductoService productoService;

    @Autowired
    private MockMvc llamadaFalsa;

    private Producto productoEjemplo;

    @BeforeEach
    void setUp() {
        productoEjemplo = new Producto();
        productoEjemplo.setId(1);
        productoEjemplo.setNombreProducto("Catán");
        productoEjemplo.setCantProducto(7);
        productoEjemplo.setPrecio(15000);
        productoEjemplo.setTipoProducto("Cartas");
    }

    @Test
    void buscarPorId_retorna200() throws Exception {
 
        when(productoService.buscarPorId(1)).thenReturn(productoEjemplo);

        llamadaFalsa.perform(get("/api/v1/productos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombreProducto").value("Catán"));
    }

    @Test
    void buscarPorId_retorna404() throws Exception {

        when(productoService.buscarPorId(99)).thenThrow(new RuntimeException("no se encontró ese producto"));

        llamadaFalsa.perform(get("/api/v1/productos/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void eliminar_retorna204() throws Exception {

        doNothing().when(productoService).eliminarProducto(1);

   
        llamadaFalsa.perform(delete("/api/v1/productos/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_retorna404() throws Exception {

        doThrow(new RuntimeException("Producto no encontrado"))
            .when(productoService).eliminarProducto(99);

        llamadaFalsa.perform(delete("/api/v1/productos/99"))
            .andExpect(status().isNotFound());
    }
}