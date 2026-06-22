package cl.duocuc.dbCompra.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duocuc.dbCompra.Client.ProductoClient;
import cl.duocuc.dbCompra.Client.UsuarioClient;
import cl.duocuc.dbCompra.Dto.ProductoDTO;
import cl.duocuc.dbCompra.Dto.UsuarioDTO;
import cl.duocuc.dbCompra.Model.Compra;
import cl.duocuc.dbCompra.Model.MetodoPago;
import cl.duocuc.dbCompra.Repository.CompraRepository;
import cl.duocuc.dbCompra.Service.CompraService;

@ExtendWith(MockitoExtension.class)
public class CompraServiceTest {

    @Mock private CompraRepository repo;
    @Mock private ProductoClient productoClient;
    @Mock private UsuarioClient usuarioClient;

    @InjectMocks
    private CompraService compraService;

    @Test
    void listaCompras_RetornaLista() {
        when(repo.findAll()).thenReturn(Arrays.asList(new Compra(), new Compra()));
        List<Compra> lista = compraService.listaCompras();
        assertEquals(2, lista.size());
    }

    @Test
    void listaCompras_RetornaListaVacia() {
        // Arrange: Configuramos el mock para que devuelva una lista vacía
        when(repo.findAll()).thenReturn(Collections.emptyList());

        // Act: Llamamos al servicio
        List<Compra> lista = compraService.listaCompras();

        // Assert: Verificamos que la lista sea vacía (tamaño 0)
        assertTrue(lista.isEmpty());
        assertEquals(0, lista.size());
    }

    @Test
    void buscarPorId_CuandoExiste_RetornaCompra() {
        Compra compra = new Compra();
        compra.setId(1);
        when(repo.findById(1)).thenReturn(Optional.of(compra));
        
        Compra resultado = compraService.buscarPorId(1);
        assertEquals(1, resultado.getId());
    }

    @Test
    void buscarPorId_CuandoNoExiste_LanzaExcepcion() {
        when(repo.findById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> compraService.buscarPorId(1));
    }

    @Test
    void guardarCompra_Exitoso() {
        // 1. Preparar el objeto MetodoPago que requiere la entidad Compra
        MetodoPago metodoPago = new MetodoPago(1, "Paypal");

        // 2. Configurar el objeto Compra
        Compra compra = new Compra();
        compra.setIdUsuario(1);
        compra.setIdProducto(1);
        compra.setMetodoPago(metodoPago); // Asignamos el objeto, no un String

        // 3. Mockear los clientes y el repositorio
        when(usuarioClient.obtenerDatosUsuario(1)).thenReturn(new UsuarioDTO());
        when(productoClient.obtenerDatosProducto(1)).thenReturn(new ProductoDTO());
        when(repo.save(any(Compra.class))).thenReturn(compra);

        // 4. Ejecutar
        Compra guardada = compraService.guardarCompra(compra);

        // 5. Verificar
        assertNotNull(guardada);
        verify(repo, times(1)).save(compra);
    }

    @Test
    void guardarCompra_FallaSiUsuarioNoExiste() {
        Compra compra = new Compra();
        compra.setIdUsuario(1);
        when(usuarioClient.obtenerDatosUsuario(1)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> compraService.guardarCompra(compra));
    }

    @Test
    void guardarCompra_FallaSiProductoNoExiste() {
        Compra compra = new Compra();
        compra.setIdUsuario(1);
        compra.setIdProducto(1);
        
        when(usuarioClient.obtenerDatosUsuario(1)).thenReturn(new UsuarioDTO());
        when(productoClient.obtenerDatosProducto(1)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> compraService.guardarCompra(compra));
    }
}
