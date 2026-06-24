package cl.friki.notificaciones;

import cl.friki.notificaciones.dto.NotificacionDTO;
import cl.friki.notificaciones.dto.NotificacionRequestDTO;
import cl.friki.notificaciones.model.Notificacion;
import cl.friki.notificaciones.repository.NotificacionRepository;
import cl.friki.notificaciones.service.NotificacionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Notificacion Service Tests")
class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    private Notificacion crearNotificacion(Long id, Long clienteId, String tipo) {
        Notificacion notif = new Notificacion(clienteId, tipo, "Mensaje de prueba");
        notif.setId(id);
        return notif;
    }

    @Test
    void findAll_retornaLista() {
        when(notificacionRepository.findAll()).thenReturn(Arrays.asList(crearNotificacion(1L, 1L, "COMPRA")));

        List<NotificacionDTO> resultado = notificacionService.findAll();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getTipo()).isEqualTo("COMPRA");
    }

    @Test
    void findById_retornaDTO() {
        Notificacion notif = crearNotificacion(1L, 1L, "ENVIO");
        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(notif));

        Optional<NotificacionDTO> resultado = notificacionService.findById(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getTipo()).isEqualTo("ENVIO");
    }

    @Test
    void findById_noExiste_retornaVacio() {
        when(notificacionRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<NotificacionDTO> resultado = notificacionService.findById(99L);

        assertThat(resultado).isEmpty();
    }

    @Test
    void findByClienteId_retornaLista() {
        when(notificacionRepository.findByClienteId(1L)).thenReturn(Arrays.asList(crearNotificacion(1L, 1L, "COMPRA")));

        List<NotificacionDTO> resultado = notificacionService.findByClienteId(1L);

        assertThat(resultado).hasSize(1);
    }

    @Test
    void create_creaYRetornaDTO() {
        NotificacionRequestDTO request = new NotificacionRequestDTO();
        request.setClienteId(1L);
        request.setTipo("COMPRA");
        request.setMensaje("Tu compra fue procesada");
        Notificacion notif = new Notificacion(1L, "COMPRA", "Tu compra fue procesada");
        notif.setId(1L);
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notif);

        NotificacionDTO resultado = notificacionService.create(request);

        assertThat(resultado.getTipo()).isEqualTo("COMPRA");
        assertThat(resultado.getMensaje()).isEqualTo("Tu compra fue procesada");
        verify(notificacionRepository).save(any(Notificacion.class));
    }

    @Test
    void marcarComoLeida_actualizaEstado() {
        Notificacion notif = crearNotificacion(1L, 1L, "COMPRA");
        notif.setLeida(false);
        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(notif));
        when(notificacionRepository.save(any(Notificacion.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<NotificacionDTO> resultado = notificacionService.marcarComoLeida(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().isLeida()).isTrue();
        verify(notificacionRepository).save(any(Notificacion.class));
    }

    @Test
    void marcarComoLeida_noExiste_retornaVacio() {
        when(notificacionRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<NotificacionDTO> resultado = notificacionService.marcarComoLeida(99L);

        assertThat(resultado).isEmpty();
        verify(notificacionRepository, never()).save(any());
    }

    @Test
    void delete_existente_retornaTrue() {
        when(notificacionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(notificacionRepository).deleteById(1L);

        boolean eliminado = notificacionService.delete(1L);

        assertThat(eliminado).isTrue();
        verify(notificacionRepository).deleteById(1L);
    }

    @Test
    void delete_noExistente_retornaFalse() {
        when(notificacionRepository.existsById(99L)).thenReturn(false);

        boolean eliminado = notificacionService.delete(99L);

        assertThat(eliminado).isFalse();
        verify(notificacionRepository, never()).deleteById(any());
    }
}
