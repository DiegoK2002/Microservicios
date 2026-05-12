package cl.friki.Reembolsos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.friki.Reembolsos.model.Reembolso;

@Repository
public interface ReembolsoRepository extends JpaRepository<Reembolso, Integer> {

    // Buscar por estado (ej: "Pendiente", "Aprobado", "Rechazado")
    List<Reembolso> findByEstado(String estado);

    // Buscar por nombre
    List<Reembolso> findByNombreReembolsoContainingIgnoreCase(String nombre);
}