package cl.duoc.Notificaciones.service;

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

import cl.duoc.Notificaciones.Model.Notificacion;
import cl.duoc.Notificaciones.Repository.NotificacionRepository;
import cl.duoc.Notificaciones.Service.NotificacionService;

@ExtendWith(MockitoExtension.class)
public class NotificacionesServiceTest {

    @Mock
    private NotificacionRepository repository;

    @InjectMocks
    private NotificacionService service;

    @Test
    void listarTodas_RetornaLista() {
        when(repository.findAll()).thenReturn(Arrays.asList(new Notificacion(), new Notificacion()));
        List<Notificacion> resultado = service.listarTodas();
        assertEquals(2, resultado.size());
    }

    @Test
    void listarTodas_RetornaListaVacia() {
        // Arrange: Configuramos el mock para que retorne una lista vacía
        when(repository.findAll()).thenReturn(Collections.emptyList());

        // Act: Llamamos al servicio
        List<Notificacion> resultado = service.listarTodas();

        // Assert: Verificamos que la lista esté vacía
        assertTrue(resultado.isEmpty());
        assertEquals(0, resultado.size());
    }

    @Test
    void buscarPorEstado_CuandoExiste_RetornaLista() {
        when(repository.findByEstado("Enviada")).thenReturn(Arrays.asList(new Notificacion()));
        List<Notificacion> resultado = service.buscarPorEstado("Enviada");
        assertFalse(resultado.isEmpty());
    }

    @Test
    void buscarPorEstado_CuandoVacio_LanzaExcepcion() {
        when(repository.findByEstado("Pendiente")).thenReturn(Collections.emptyList());
        assertThrows(RuntimeException.class, () -> service.buscarPorEstado("Pendiente"));
    }

    @Test
    void crear_NotificacionValida_RetornaGuardada() {
        Notificacion notif = new Notificacion(1, "Usuario", "Mensaje", "22-06-2026", "Enviada");
        when(repository.save(any(Notificacion.class))).thenReturn(notif);

        Notificacion resultado = service.crear(notif);
        assertEquals("Usuario", resultado.getDestinatario());
    }

    @Test
    void crear_ValidacionFalla_LanzaExcepcion() {
        Notificacion notif = new Notificacion(1, "", "", "22-06-2026", "Pendiente");
        assertThrows(RuntimeException.class, () -> service.crear(notif));
    }

    @Test
    void actualizar_Exitoso() {
        Notificacion existente = new Notificacion(1, "A", "M", "F", "E");
        Notificacion nuevosDatos = new Notificacion(1, "B", "M2", "F2", "E2");
        
        when(repository.findById(1)).thenReturn(Optional.of(existente));
        when(repository.save(any(Notificacion.class))).thenReturn(nuevosDatos);

        Notificacion resultado = service.actualizar(1, nuevosDatos);
        assertEquals("B", resultado.getDestinatario());
    }

    @Test
    void actualizar_CuandoNoExiste_LanzaExcepcion() {
        // Arrange: Preparamos datos, pero simulamos que no encuentra la notificación
        Integer idInexistente = 99;
        Notificacion datosNuevos = new Notificacion(idInexistente, "B", "M2", "F2", "E2");
    
        // Configuramos el mock para que devuelva Optional.empty() al buscar por ID
        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        // Act & Assert: Verificamos que el servicio lanza la RuntimeException
        assertThrows(RuntimeException.class, () -> {
            service.actualizar(idInexistente, datosNuevos);
        });

        // Verificamos que nunca se intentó guardar en el repositorio
        verify(repository, never()).save(any(Notificacion.class));
    }

    @Test
    void cambiarEstado_Exitoso() {
        Notificacion notif = new Notificacion(1, "A", "M", "F", "Pendiente");
        when(repository.findById(1)).thenReturn(Optional.of(notif));
        when(repository.save(any(Notificacion.class))).thenAnswer(i -> i.getArguments()[0]);

        Notificacion resultado = service.cambiarEstado(1, "Enviada");
        assertEquals("Enviada", resultado.getEstado());
    }

    @Test
    void cambiarEstado_CuandoNoExiste_LanzaExcepcion() {
        // Arrange: ID inexistente
        Integer idInexistente = 999;
    
        // Configuramos el mock para que no encuentre la notificación
        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        // Act & Assert: Verificamos que el servicio lanza la RuntimeException
        // Al fallar buscarPorId, la ejecución se detiene antes de llamar a save
        assertThrows(RuntimeException.class, () -> {
            service.cambiarEstado(idInexistente, "Enviada");
        });

        // Verificamos que nunca se intentó guardar nada
        verify(repository, never()).save(any(Notificacion.class));
    }

    @Test
    void eliminar_Exitoso() {
        when(repository.existsById(1)).thenReturn(true);
        service.eliminar(1);
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_CuandoNoExiste_LanzaExcepcion() {
        when(repository.existsById(1)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> service.eliminar(1));
    }
}
