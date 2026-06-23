package cl.duoc.Reembolsos;

import cl.duoc.Reembolsos.Model.Reembolso;
import cl.duoc.Reembolsos.Repository.ReembolsoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Reembolso Repository Tests")
class ReembolsoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReembolsoRepository reembolsoRepository;

    private Reembolso crearReembolso(String nombre, Integer monto, String estado) {
        Reembolso r = new Reembolso();
        r.setIdCompra(1);
        r.setNombreReembolso(nombre);
        r.setMonto(monto);
        r.setFecha("2024-01-15");
        r.setEstado(estado);
        r.setDescripcion("Descripción de prueba");
        return r;
    }

    @Test
    void guardarYRecuperarReembolso() {
        Reembolso reembolso = crearReembolso("Reembolso Test", 50000, "Pendiente");
        Reembolso guardado = entityManager.persistAndFlush(reembolso);

        Optional<Reembolso> encontrado = reembolsoRepository.findById(guardado.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNombreReembolso()).isEqualTo("Reembolso Test");
        assertThat(encontrado.get().getEstado()).isEqualTo("Pendiente");
    }

    @Test
    void findByEstado_retornaReembolsosFiltrados() {
        entityManager.persistAndFlush(crearReembolso("R1", 10000, "Pendiente"));
        entityManager.persistAndFlush(crearReembolso("R2", 20000, "Aprobado"));
        entityManager.persistAndFlush(crearReembolso("R3", 30000, "Pendiente"));

        List<Reembolso> pendientes = reembolsoRepository.findByEstado("Pendiente");

        assertThat(pendientes).hasSize(2);
        assertThat(pendientes).allMatch(r -> r.getEstado().equals("Pendiente"));
    }

    @Test
    void listarTodosLosReembolsos() {
        entityManager.persistAndFlush(crearReembolso("R1", 10000, "Pendiente"));
        entityManager.persistAndFlush(crearReembolso("R2", 20000, "Aprobado"));

        List<Reembolso> reembolsos = reembolsoRepository.findAll();

        assertThat(reembolsos).hasSize(2);
    }

    @Test
    void eliminarReembolso() {
        Reembolso reembolso = crearReembolso("Temp", 5000, "Rechazado");
        Reembolso guardado = entityManager.persistAndFlush(reembolso);

        reembolsoRepository.deleteById(guardado.getId());

        assertThat(reembolsoRepository.findById(guardado.getId())).isEmpty();
    }
}
