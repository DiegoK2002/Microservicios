package cl.friki.Producto.service;

import static org.junit.jupiter.api.Assertions.*;
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

import cl.friki.Producto.model.Producto;
import cl.friki.Producto.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService service;

    // Datos de prueba
    private Producto crearProducto() {
        Producto p = new Producto();
        p.setId(1);
        p.setNombreProducto("Laptop");
        p.setCantProducto(10);
        p.setPrecio(500000);
        p.setTipoProducto("Electronico");
        return p;
    }

    // listarProductos
    @Test
    void listarProducts_conProductos_retornaLista() {
        when(productoRepository.findAll()).thenReturn(Arrays.asList(crearProducto()));

        List<Producto> resultado = service.listarProducts();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Laptop", resultado.get(0).getNombreProducto());
    }

    @Test
    void listarProducts_sinProductos_retornaListaVacia() {
        when(productoRepository.findAll()).thenReturn(Collections.emptyList());

        List<Producto> resultado = service.listarProducts();

        assertTrue(resultado.isEmpty());
    }

    // buscarPorId
    @Test
    void buscarPorId_existente_retornaProducto() {
        when(productoRepository.findById(1)).thenReturn(Optional.of(crearProducto()));

        Producto resultado = service.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Laptop", resultado.getNombreProducto());
    }

    @Test
    void buscarPorId_noExistente_lanzaExcepcion() {
        when(productoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.buscarPorId(99));
    }

    // crearProducto
    @Test
    void crearProducto_guardaYRetornaProducto() {
        Producto nuevo = crearProducto();
        when(productoRepository.save(nuevo)).thenReturn(nuevo);

        Producto resultado = service.crearProducto(nuevo);

        assertNotNull(resultado);
        assertEquals("Laptop", resultado.getNombreProducto());
        verify(productoRepository, times(1)).save(nuevo);
    }

    // actualizarProducto
    @Test
    void actualizarProducto_existente_retornaActualizado() {
        Producto anterior = crearProducto();
        Producto actualizado = new Producto(1, "Laptop Pro", 5, 700000, "Electronico");
        when(productoRepository.findById(1)).thenReturn(Optional.of(anterior));
        when(productoRepository.save(any(Producto.class))).thenReturn(actualizado);

        Producto resultado = service.actualizarProducto(1, actualizado);

        assertEquals("Laptop Pro", resultado.getNombreProducto());
        assertEquals(700000, resultado.getPrecio());
    }

    @Test
    void actualizarProducto_noExistente_lanzaExcepcion() {
        when(productoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
            service.actualizarProducto(99, crearProducto())
        );
    }

    // eliminarProducto
    @Test
    void eliminarProducto_existente_eliminaCorrectamente() {
        when(productoRepository.existsById(1)).thenReturn(true);

        service.eliminarProducto(1);

        verify(productoRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarProducto_noExistente_lanzaExcepcion() {
        when(productoRepository.existsById(99)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> service.eliminarProducto(99));
    }

    // buscarPorNombre
    @Test
    void buscarPorNombre_encontrado_retornaLista() {
        when(productoRepository.findByNombreProducto("Laptop"))
            .thenReturn(Arrays.asList(crearProducto()));

        List<Producto> resultado = service.buscarPorNombre("Laptop");

        assertFalse(resultado.isEmpty());
        assertEquals("Laptop", resultado.get(0).getNombreProducto());
    }

    @Test
    void buscarPorNombre_noEncontrado_retornaListaVacia() {
        when(productoRepository.findByNombreProducto("XYZ"))
            .thenReturn(Collections.emptyList());

        List<Producto> resultado = service.buscarPorNombre("XYZ");

        assertTrue(resultado.isEmpty());
    }
}