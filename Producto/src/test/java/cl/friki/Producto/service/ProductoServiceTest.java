package cl.friki.Producto.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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
    private ProductoService productoService;

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

    // listarProducts
    @Test
    void listar() {
      
        List<Producto> listaFalsa = new ArrayList<>();
        listaFalsa.add(productoEjemplo);
        when(productoRepository.findAll()).thenReturn(listaFalsa);

   
        List<Producto> listaProductos = productoService.listarProducts();

     
        assertEquals(1, listaProductos.size());
        assertEquals("Catán", listaProductos.get(0).getNombreProducto());
    }

    // buscarPorId
    @Test
    void buscarPorId_encontrado() {
      
        when(productoRepository.findById(1)).thenReturn(Optional.of(productoEjemplo));

     
        Producto resultado = productoService.buscarPorId(1);

   
        assertEquals(1, resultado.getId());
        assertEquals("Catán", resultado.getNombreProducto());
    }

    @Test
    void buscarPorId_noEncontrado() {
    
        when(productoRepository.findById(99)).thenReturn(Optional.empty());

  
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            productoService.buscarPorId(99);
        });

        assertEquals("no se encontró ese producto", error.getMessage());
    }

    // crearProducto
    @Test
    void crearProducto() {
   
        when(productoRepository.save(productoEjemplo)).thenReturn(productoEjemplo);

  
        Producto resultado = productoService.crearProducto(productoEjemplo);

   
        assertEquals("Catán", resultado.getNombreProducto());
        verify(productoRepository, times(1)).save(productoEjemplo);
    }

    // actualizarProducto
    @Test
    void actualizarProducto_encontrado() {
       
        Producto productoActualizado = new Producto();
        productoActualizado.setNombreProducto("Catan Expansión");
        productoActualizado.setCantProducto(3);
        productoActualizado.setPrecio(25000);
        productoActualizado.setTipoProducto("Cartas");

        when(productoRepository.findById(1)).thenReturn(Optional.of(productoEjemplo));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoActualizado);

   
        Producto resultado = productoService.actualizarProducto(1, productoActualizado);

   
        assertEquals("Catan Expansión", resultado.getNombreProducto());
        assertEquals(25000, resultado.getPrecio());
    }

    @Test
    void actualizarProducto_noEncontrado() {
       
        when(productoRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            productoService.actualizarProducto(99, productoEjemplo);
        });

        assertEquals("Producto no encontrado", error.getMessage());
    }

    // eliminarProducto
    @Test
    void eliminarProducto_encontrado() {
     
        when(productoRepository.existsById(1)).thenReturn(true);

     
        productoService.eliminarProducto(1);

        verify(productoRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarProducto_noEncontrado() {
       
        when(productoRepository.existsById(99)).thenReturn(false);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            productoService.eliminarProducto(99);
        });

        assertEquals("Producto no encontrado", error.getMessage());
    }

    // buscarPorNombre
    @Test
    void buscarPorNombre_encontrado() {
  
        List<Producto> listaFalsa = new ArrayList<>();
        listaFalsa.add(productoEjemplo);
        when(productoRepository.findByNombreProducto("Catán")).thenReturn(listaFalsa);

      
        List<Producto> resultado = productoService.buscarPorNombre("Catán");

     
        assertEquals(1, resultado.size());
        assertEquals("Catán", resultado.get(0).getNombreProducto());
    }

    @Test
    void buscarPorNombre_noEncontrado() {

        when(productoRepository.findByNombreProducto("XYZ")).thenReturn(new ArrayList<>());

  
        List<Producto> resultado = productoService.buscarPorNombre("XYZ");

        assertTrue(resultado.isEmpty());
    }
}