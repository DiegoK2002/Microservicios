package cl.duocuc.dbReportes.Service;

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

import cl.duocuc.dbReportes.Model.Reportes;
import cl.duocuc.dbReportes.Repository.ReportesRepository;

@ExtendWith(MockitoExtension.class)
public class ReportesServiceTest {

    @Mock
    private ReportesRepository repo; // Mock del repositorio

    @InjectMocks
    private ReportesService service; // Servicio a probar

    @Test
    void listaReporte_RetornaLista() {
        Reportes r1 = new Reportes(1, 100);
        Reportes r2 = new Reportes(2, 200);
        when(repo.findAll()).thenReturn(Arrays.asList(r1, r2)); // Simulación findAll

        List<Reportes> resultado = service.listaReporte();

        assertEquals(2, resultado.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void listaReporte_RetornaListaVacia() {
        // 1. Arrange: Simulamos que el repositorio retorna una lista vacía
        when(repo.findAll()).thenReturn(Collections.emptyList());

        // 2. Act: Llamamos al servicio
        List<Reportes> resultado = service.listaReporte();

        // 3. Assert: Verificamos que la lista esté vacía y no sea nula
        assertNotNull(resultado, "La lista no debería ser nula");
        assertTrue(resultado.isEmpty(), "La lista debería estar vacía");
        verify(repo, times(1)).findAll();
    }

    @Test
    void buscarPorId_Existente_RetornaReporte() {
        Reportes r1 = new Reportes(1, 100);
        when(repo.findById(1)).thenReturn(Optional.of(r1)); // Simulación findById

        Reportes resultado = service.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(100, resultado.getIdCompra());
    }

    @Test
    void buscarPorId_NoExiste_LanzaExcepcion() {
        when(repo.findById(99)).thenReturn(Optional.empty()); // Simulación caso no encontrado

        assertThrows(RuntimeException.class, () -> service.buscarPorId(99));
    }

    @Test
    void buscarPorIdCompra_RetornaReporte() {
        Reportes r1 = new Reportes(1, 100);
        when(repo.findByIdCompra(100)).thenReturn(Optional.of(r1)); // Simulación findByIdCompra

        Reportes resultado = service.buscarPorIdCompra(100);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    void buscarPorIdCompra_NoEncontrado_RetornaNull() {
        // 1. Arrange: Simulamos que el repositorio no encuentra nada para ese ID
        Integer idCompraInexistente = 999;
        when(repo.findByIdCompra(idCompraInexistente)).thenReturn(Optional.empty());

        // 2. Act: Llamamos al servicio
        Reportes resultado = service.buscarPorIdCompra(idCompraInexistente);

        // 3. Assert: Verificamos que el resultado sea null
        assertNull(resultado, "El resultado debería ser null cuando no se encuentra el reporte");
        verify(repo, times(1)).findByIdCompra(idCompraInexistente);
    }

    @Test
    void guardarReportes_Exitoso() {
        Reportes r1 = new Reportes(null, 500);
        when(repo.save(any(Reportes.class))).thenReturn(new Reportes(1, 500)); // Simulación save

        Reportes guardado = service.guardarReportes(r1);

        assertNotNull(guardado.getId());
        verify(repo, times(1)).save(r1);
    }

    @Test
    void guardarReportes_FallaAlGuardar_LanzaExcepcion() {
        // 1. Arrange: Preparamos el objeto y configuramos el mock para fallar
        Reportes r1 = new Reportes(null, 500);
        when(repo.save(any(Reportes.class)))
            .thenThrow(new RuntimeException("Error al guardar en base de datos"));

        // 2. Act & Assert: Verificamos que se lanza la excepción esperada
        assertThrows(RuntimeException.class, () -> {
            service.guardarReportes(r1);
        });
    
        // Verificamos que se intentó guardar al menos una vez
        verify(repo, times(1)).save(r1);
    }
}
