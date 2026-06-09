package cl.duoc.Notificaciones.Repository;

import cl.duoc.Notificaciones.Model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {

    // Buscar por estado (Enviada / Pendiente / Fallida)
    List<Notificacion> findByEstado(String estado);

    // Buscar por destinatario
    List<Notificacion> findByDestinatarioContainingIgnoreCase(String destinatario);
}
