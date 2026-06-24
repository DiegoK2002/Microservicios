package cl.duoc.Remuneraciones.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.Remuneraciones.model.Remuneracion;

@Repository
public interface RemuneracionRepository extends JpaRepository<Remuneracion, Integer> {

    // Buscar por estado (Pagado, Pendiente, Retrasado)
    List<Remuneracion> findByEstado(String estado);

    // Buscar por nombre de empleado
    List<Remuneracion> findByNombreEmpleadoContainingIgnoreCase(String nombre);
}