package cl.duoc.Notificaciones.Service;

import cl.duoc.Notificaciones.DTO.NotificacionDTO;
import cl.duoc.Notificaciones.Model.Notificacion;
import cl.duoc.Notificaciones.Repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository repository;

    // ── Listar todas ─────────────────────────────────────────────────────────
    public List<Notificacion> listarTodas() {
        return repository.findAll();
    }

    // ── Buscar por ID ────────────────────────────────────────────────────────
    public Notificacion buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con id: " + id));
    }

    // ── Buscar por estado ────────────────────────────────────────────────────
    public List<Notificacion> buscarPorEstado(String estado) {
        List<Notificacion> lista = repository.findByEstado(estado);
        if (lista.isEmpty()) {
            throw new RuntimeException("No hay notificaciones con estado: " + estado);
        }
        return lista;
    }

    // ── Buscar por destinatario ──────────────────────────────────────────────
    public List<Notificacion> buscarPorDestinatario(String destinatario) {
        List<Notificacion> lista = repository.findByDestinatarioContainingIgnoreCase(destinatario);
        if (lista.isEmpty()) {
            throw new RuntimeException("No hay notificaciones para: " + destinatario);
        }
        return lista;
    }

    // ── Crear ────────────────────────────────────────────────────────────────
    public Notificacion crear(Notificacion notificacion) {
        if (notificacion.getDestinatario() == null || notificacion.getDestinatario().isBlank()) {
            throw new RuntimeException("El destinatario es obligatorio");
        }
        if (notificacion.getMensaje() == null || notificacion.getMensaje().isBlank()) {
            throw new RuntimeException("El mensaje es obligatorio");
        }
        if (notificacion.getEstado() == null || notificacion.getEstado().isBlank()) {
            notificacion.setEstado("Pendiente");
        }
        return repository.save(notificacion);
    }

    // ── Actualizar ───────────────────────────────────────────────────────────
    public Notificacion actualizar(Integer id, Notificacion datos) {
        Notificacion existente = buscarPorId(id);
        existente.setDestinatario(datos.getDestinatario());
        existente.setMensaje(datos.getMensaje());
        existente.setFecha(datos.getFecha());
        existente.setEstado(datos.getEstado());
        return repository.save(existente);
    }

    // ── Cambiar estado ───────────────────────────────────────────────────────
    public Notificacion cambiarEstado(Integer id, String nuevoEstado) {
        Notificacion notificacion = buscarPorId(id);
        notificacion.setEstado(nuevoEstado);
        return repository.save(notificacion);
    }

    // ── Eliminar ─────────────────────────────────────────────────────────────
    public void eliminar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Notificación no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }

    // ── Convertir a DTO ──────────────────────────────────────────────────────
    public NotificacionDTO toDTO(Notificacion n) {
        return new NotificacionDTO(
                n.getId(),
                n.getDestinatario(),
                n.getMensaje(),
                n.getFecha(),
                n.getEstado()
        );
    }
}
