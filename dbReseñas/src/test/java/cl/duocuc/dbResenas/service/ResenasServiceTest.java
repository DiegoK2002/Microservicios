package cl.duocuc.dbResenas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duocuc.dbResenas.Client.ProductoClient;
import cl.duocuc.dbResenas.Dto.ProductoDTO;
import cl.duocuc.dbResenas.Model.Resenas;
import cl.duocuc.dbResenas.Repository.ResenasRepository;
import cl.duocuc.dbResenas.Service.ResenasService;

@ExtendWith(MockitoExtension.class)
public class ResenasServiceTest {

    @Mock
    private ResenasRepository repo;

    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private ResenasService service;

    @Test
    void listaReseñas_RetornaLista() {
        when(repo.findAll()).thenReturn(Arrays.asList(new Resenas(), new Resenas()));
        List<Resenas> lista = service.listaReseñas();
        assertEquals(2, lista.size());
    }

    @Test
    void listaReseñas_RetornaListaVacia() {
        // 1. Arrange: Simulamos que el repositorio está vacío
        when(repo.findAll()).thenReturn(Collections.emptyList());

        // 2. Act: Llamamos al servicio
        List<Resenas> lista = service.listaReseñas();

        // 3. Assert: Verificamos que la lista esté vacía
        assertTrue(lista.isEmpty(), "La lista debería estar vacía");
        assertEquals(0, lista.size());
    }

    @Test
    void buscarPorId_Existente_RetornaResena() {
        Resenas r = new Resenas(1, "Producto", 1, 5, "Genial");
        when(repo.findById(1)).thenReturn(Optional.of(r));
        
        Resenas resultado = service.buscarPorId(1);
        assertEquals("Producto", resultado.getNombreProducto());
    }

    @Test
    void buscarPorId_NoExistente_LanzaExcepcion() {
        // 1. Arrange: Simulamos que el repositorio no encuentra la reseña con ID 99
        when(repo.findById(99)).thenReturn(Optional.empty());

        // 2. Act & Assert: Verificamos que se lanza la RuntimeException esperada
        assertThrows(RuntimeException.class, () -> {
            service.buscarPorId(99);
        });
    }

    @Test
    void guardarResenas_Exitoso() {
        Resenas resena = new Resenas(null, null, 1, 5, "Excelente");
        ProductoDTO p = new ProductoDTO(1, "Producto A", 100);
        
        when(productoClient.verificarProductoExistente(1)).thenReturn(p);
        when(repo.save(any(Resenas.class))).thenReturn(resena);

        Resenas guardada = service.guardarResenas(resena);
        assertEquals("Producto A", guardada.getNombreProducto());
    }

    @Test
    void guardarResenas_ProductoNoExiste_LanzaExcepcion() {
        Resenas resena = new Resenas(null, null, 99, 5, "Mal");
        when(productoClient.verificarProductoExistente(99)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> service.guardarResenas(resena));
    }

    @Test
    void eliminarResena_Exitoso() {
        when(repo.existsById(1)).thenReturn(true);
        service.eliminarResena(1);
        verify(repo, times(1)).deleteById(1);
    }

    @Test
    void eliminarResena_CuandoNoExiste_LanzaExcepcion() {
        // 1. Arrange: Simulamos que el repositorio indica que el ID no existe
        Integer idInexistente = 99;
        when(repo.existsById(idInexistente)).thenReturn(false);

        // 2. Act & Assert: Verificamos que se lanza la excepción esperada
        assertThrows(RuntimeException.class, () -> {
            service.eliminarResena(idInexistente);
        });

        // 3. Verify: Aseguramos que nunca se llamó a deleteById
        verify(repo, never()).deleteById(idInexistente);
    }
}
