package cl.duoc.Reembolsos.Repository;

import cl.duoc.Reembolsos.Model.Reembolso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReembolsoRepository extends JpaRepository<Reembolso, Integer> {

    // Buscar por estado (ej: "Pendiente", "Aprobado", "Rechazado")
    List<Reembolso> findByEstado(String estado);
}
