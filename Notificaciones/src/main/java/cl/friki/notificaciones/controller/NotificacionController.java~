package cl.friki.notificaciones.controller;

import cl.friki.notificaciones.dto.NotificacionDTO;
import cl.friki.notificaciones.dto.NotificacionRequestDTO;
import cl.friki.notificaciones.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    // GET /api/v1/notificaciones
    @GetMapping
    public ResponseEntity<List<NotificacionDTO>> getAll() {
        return ResponseEntity.ok(notificacionService.findAll());
    }

    // GET /api/v1/notificaciones/{id}
    @GetMapping("/{id}")
    public ResponseEntity<NotificacionDTO> getById(@PathVariable Long id) {
        return notificacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/v1/notificaciones/cliente/{clienteId}
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<NotificacionDTO>> getByClienteId(@PathVariable Long clienteId) {
        return ResponseEntity.ok(notificacionService.findByClienteId(clienteId));
    }

    // POST /api/v1/notificaciones
    // Endpoint consumido por el microservicio Clientes a través de Feign
    @PostMapping
    public ResponseEntity<NotificacionDTO> create(@RequestBody NotificacionRequestDTO request) {
        NotificacionDTO creada = notificacionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    // PUT /api/v1/notificaciones/{id}/leida
    @PutMapping("/{id}/leida")
    public ResponseEntity<NotificacionDTO> marcarComoLeida(@PathVariable Long id) {
        return notificacionService.marcarComoLeida(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/v1/notificaciones/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return notificacionService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
