package cl.friki.notificaciones;

import cl.friki.notificaciones.model.Notificacion;
import cl.friki.notificaciones.repository.NotificacionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Notificacion Repository Tests")
class NotificacionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private NotificacionRepository notificacionRepository;

    private Notificacion crearNotificacion(Long clienteId, String tipo, boolean leida) {
        Notificacion notif = new Notificacion(clienteId, tipo, "Mensaje de prueba");
        notif.setLeida(leida);
        return notif;
    }

    @Test
    void guardarYRecuperarNotificacion() {
        Notificacion notif = crearNotificacion(1L, "COMPRA", false);
        Notificacion guardada = entityManager.persistAndFlush(notif);

        Optional<Notificacion> encontrada = notificacionRepository.findById(guardada.getId());

        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getTipo()).isEqualTo("COMPRA");
        assertThat(encontrada.get().isLeida()).isFalse();
    }

    @Test
    void findByClienteId_retornaNotificaciones() {
        entityManager.persistAndFlush(crearNotificacion(1L, "COMPRA", false));
        entityManager.persistAndFlush(crearNotificacion(1L, "ENVIO", true));
        entityManager.persistAndFlush(crearNotificacion(2L, "COMPRA", false));

        List<Notificacion> resultado = notificacionRepository.findByClienteId(1L);

        assertThat(resultado).hasSize(2);
    }

    @Test
    void findByClienteIdAndLeida_retornaNoLeidas() {
        entityManager.persistAndFlush(crearNotificacion(1L, "COMPRA", false));
        entityManager.persistAndFlush(crearNotificacion(1L, "ENVIO", true));

        List<Notificacion> noLeidas = notificacionRepository.findByClienteIdAndLeida(1L, false);

        assertThat(noLeidas).hasSize(1);
        assertThat(noLeidas.get(0).getTipo()).isEqualTo("COMPRA");
    }

    @Test
    void eliminarNotificacion() {
        Notificacion notif = crearNotificacion(1L, "TEST", false);
        Notificacion guardada = entityManager.persistAndFlush(notif);

        notificacionRepository.deleteById(guardada.getId());

        assertThat(notificacionRepository.findById(guardada.getId())).isEmpty();
    }
}
