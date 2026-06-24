package cl.duoc.Remuneraciones;

import cl.duoc.Remuneraciones.model.Remuneracion;
import cl.duoc.Remuneraciones.repository.RemuneracionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Remuneracion Repository Tests")
class RemuneracionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RemuneracionRepository remuneracionRepository;

    private Remuneracion crearRemuneracion(String empleado, Double salario, String estado) {
        Remuneracion r = new Remuneracion();
        r.setNombreEmpleado(empleado);
        r.setSalarioBase(salario);
        r.setBonificacion(50000.0);
        r.setDescuentos(20000.0);
        r.setFechaPago("2024-01-31");
        r.setEstado(estado);
        r.setDescripcion("Remuneración de prueba");
        return r;
    }

    @Test
    void guardarYRecuperarRemuneracion() {
        Remuneracion remuneracion = crearRemuneracion("Juan Perez", 800000.0, "PENDIENTE");
        Remuneracion guardada = entityManager.persistAndFlush(remuneracion);

        Optional<Remuneracion> encontrada = remuneracionRepository.findById(guardada.getId());

        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getNombreEmpleado()).isEqualTo("Juan Perez");
        assertThat(encontrada.get().getEstado()).isEqualTo("PENDIENTE");
    }

    @Test
    void findByEstado_retornaRemuneracionesFiltradas() {
        entityManager.persistAndFlush(crearRemuneracion("Ana", 700000.0, "PENDIENTE"));
        entityManager.persistAndFlush(crearRemuneracion("Beto", 900000.0, "PAGADO"));
        entityManager.persistAndFlush(crearRemuneracion("Cata", 850000.0, "PENDIENTE"));

        List<Remuneracion> pendientes = remuneracionRepository.findByEstado("PENDIENTE");

        assertThat(pendientes).hasSize(2);
        assertThat(pendientes).allMatch(r -> r.getEstado().equals("PENDIENTE"));
    }

    @Test
    void findByNombreEmpleadoContainingIgnoreCase_buscaParcialSinDistinguirMayusculas() {
        entityManager.persistAndFlush(crearRemuneracion("Maria Gonzalez", 750000.0, "PAGADO"));
        entityManager.persistAndFlush(crearRemuneracion("Pedro Soto", 600000.0, "PENDIENTE"));

        List<Remuneracion> resultado = remuneracionRepository.findByNombreEmpleadoContainingIgnoreCase("maria");

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombreEmpleado()).isEqualTo("Maria Gonzalez");
    }

    @Test
    void eliminarRemuneracion() {
        Remuneracion remuneracion = crearRemuneracion("Temp", 500000.0, "PENDIENTE");
        Remuneracion guardada = entityManager.persistAndFlush(remuneracion);

        remuneracionRepository.deleteById(guardada.getId());

        assertThat(remuneracionRepository.findById(guardada.getId())).isEmpty();
    }
}
