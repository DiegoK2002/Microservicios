package cl.duoc.Notificaciones.Controller;

import cl.duoc.Notificaciones.DTO.NotificacionDTO;
import cl.duoc.Notificaciones.Model.Notificacion;
import cl.duoc.Notificaciones.Service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    // GET /api/v1/notificaciones
    @GetMapping
    public ResponseEntity<List<Notificacion>> listar() {
        List<Notificacion> lista = service.listarTodas();
        return lista.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(lista);
    }

    // GET /api/v1/notificaciones/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/v1/notificaciones/dto/{id}
    @GetMapping("/dto/{id}")
    public ResponseEntity<NotificacionDTO> obtenerDTO(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.toDTO(service.buscarPorId(id)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/v1/notificaciones/estado/{estado}
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Notificacion>> buscarPorEstado(@PathVariable String estado) {
        try {
            return ResponseEntity.ok(service.buscarPorEstado(estado));
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    // GET /api/v1/notificaciones/destinatario/{destinatario}
    @GetMapping("/destinatario/{destinatario}")
    public ResponseEntity<List<Notificacion>> buscarPorDestinatario(@PathVariable String destinatario) {
        try {
            return ResponseEntity.ok(service.buscarPorDestinatario(destinatario));
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    // POST /api/v1/notificaciones
    @PostMapping
    public ResponseEntity<Notificacion> crear(@RequestBody Notificacion notificacion) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(notificacion));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT /api/v1/notificaciones/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Notificacion> actualizar(@PathVariable Integer id,
                                                    @RequestBody Notificacion notificacion) {
        try {
            return ResponseEntity.ok(service.actualizar(id, notificacion));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PATCH /api/v1/notificaciones/{id}/estado/{nuevoEstado}
    @PatchMapping("/{id}/estado/{nuevoEstado}")
    public ResponseEntity<Notificacion> cambiarEstado(@PathVariable Integer id,
                                                       @PathVariable String nuevoEstado) {
        try {
            return ResponseEntity.ok(service.cambiarEstado(id, nuevoEstado));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/v1/notificaciones/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
