package cl.duocuc.dbEnvio.Service;

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

import cl.duocuc.dbEnvio.Client.CompraClient;
import cl.duocuc.dbEnvio.Client.NotificacionClient;
import cl.duocuc.dbEnvio.Dto.CompraDTO;
import cl.duocuc.dbEnvio.Model.Envio;
import cl.duocuc.dbEnvio.Repository.EnvioRepository;

@ExtendWith(MockitoExtension.class)
public class EnvioServiceTest {

    @Mock private EnvioRepository repo;
    @Mock private CompraClient compraClient;
    @Mock private NotificacionClient notificacionClient;

    @InjectMocks
    private EnvioService service;

    @Test
    void listaEnvios_RetornaLista() {
        // 1. Arrange: Simulamos que el repositorio tiene envíos
        Envio envio1 = new Envio(1, 100, null);
        Envio envio2 = new Envio(2, 200, null);
        when(repo.findAll()).thenReturn(Arrays.asList(envio1, envio2));

        // 2. Act: Llamamos al servicio
        List<Envio> lista = service.listaEnvios();

        // 3. Assert: Verificamos que la lista tenga los elementos esperados
        assertEquals(2, lista.size());
        assertEquals(1, lista.get(0).getId());
        verify(repo, times(1)).findAll();
    }

    @Test
    void listaEnvios_RetornaListaVacia() {
        // 1. Arrange: Simulamos que el repositorio no tiene elementos
        when(repo.findAll()).thenReturn(Collections.emptyList());

        // 2. Act: Llamamos al servicio
        List<Envio> lista = service.listaEnvios();

        // 3. Assert: Verificamos que la lista esté vacía
        assertTrue(lista.isEmpty(), "La lista debería estar vacía");
        assertEquals(0, lista.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void guardarEnvio_Exitoso() {
        Envio envio = new Envio(null, 1, null);
        CompraDTO compra = new CompraDTO(); // Simulamos compra encontrada
        
        when(compraClient.obtenerCompraPorId(1)).thenReturn(compra);
        when(repo.save(any(Envio.class))).thenReturn(new Envio(1, 1, null));

        Envio guardado = service.guardarEnvio(envio);
        
        assertNotNull(guardado);
        verify(notificacionClient, times(1)).enviarAlertaEnvio(any());
    }

    @Test
    void guardarEnvio_CompraNoExiste_LanzaExcepcion() {
        Envio envio = new Envio(null, 999, null);
        // Simulamos que el cliente retorna null (no existe la compra)
        when(compraClient.obtenerCompraPorId(999)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> service.guardarEnvio(envio));
        verify(repo, never()).save(any());
    }

    @Test
    void buscarPorId_Existente_RetornaEnvio() {
        // 1. Arrange: Preparamos un objeto de prueba y configuramos el mock
        Integer id = 1;
        Envio envioEsperado = new Envio(id, 100, null);
        when(repo.findById(id)).thenReturn(Optional.of(envioEsperado));

        // 2. Act: Llamamos al servicio
        Envio resultado = service.buscarPorId(id);

        // 3. Assert: Verificamos que los datos coincidan
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals(100, resultado.getIdCompra());
        verify(repo, times(1)).findById(id);
    }

    @Test
    void buscarPorId_NoEncontrado_LanzaExcepcion() {
        when(repo.findById(99)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.buscarPorId(99));
    }
}
