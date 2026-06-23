package cl.friki.notificaciones.service;

import cl.friki.notificaciones.dto.NotificacionDTO;
import cl.friki.notificaciones.dto.NotificacionRequestDTO;
import cl.friki.notificaciones.model.Notificacion;
import cl.friki.notificaciones.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    // ── Listar todas ──────────────────────────────────────────────────────────
    public List<NotificacionDTO> findAll() {
        return notificacionRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ── Buscar por ID ─────────────────────────────────────────────────────────
    public Optional<NotificacionDTO> findById(Long id) {
        return notificacionRepository.findById(id).map(this::toDTO);
    }

    // ── Buscar por cliente ────────────────────────────────────────────────────
    public List<NotificacionDTO> findByClienteId(Long clienteId) {
        return notificacionRepository.findByClienteId(clienteId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ── Crear (llamado normalmente desde Clientes vía Feign) ─────────────────
    public NotificacionDTO create(NotificacionRequestDTO request) {
        Notificacion nueva = new Notificacion(
                request.getClienteId(),
                request.getTipo(),
                request.getMensaje()
        );
        return toDTO(notificacionRepository.save(nueva));
    }

    // ── Marcar como leída ─────────────────────────────────────────────────────
    public Optional<NotificacionDTO> marcarComoLeida(Long id) {
        return notificacionRepository.findById(id).map(n -> {
            n.setLeida(true);
            return toDTO(notificacionRepository.save(n));
        });
    }

    // ── Eliminar ──────────────────────────────────────────────────────────────
    public boolean delete(Long id) {
        if (notificacionRepository.existsById(id)) {
            notificacionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ── Conversión Entity -> DTO ──────────────────────────────────────────────
    private NotificacionDTO toDTO(Notificacion n) {
        return new NotificacionDTO(
                n.getId(),
                n.getClienteId(),
                n.getTipo(),
                n.getMensaje(),
                n.getFechaEnvio(),
                n.isLeida()
        );
    }
}
