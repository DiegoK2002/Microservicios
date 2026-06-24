package cl.friki.notificaciones.controller;

import cl.friki.notificaciones.dto.NotificacionDTO;
import cl.friki.notificaciones.dto.NotificacionRequestDTO;
import cl.friki.notificaciones.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones")
@Tag(name = "Notificaciones", description = "Operaciones sobre las notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    // GET /api/v1/notificaciones
    @GetMapping
    @Operation(summary = "Retona la lista completa de notificaciones")
    public ResponseEntity<List<NotificacionDTO>> getAll() {
        return ResponseEntity.ok(notificacionService.findAll());
    }

    // GET /api/v1/notificaciones/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Retona una notificación mediante ID", description = "Retorna una notificación mediante un ID proporcionado")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Notificación encontrado"),
                            @ApiResponse(responseCode = "404", description = "Notificación no encontrado"),
                            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<NotificacionDTO> getById(@PathVariable Long id) {
        return notificacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/v1/notificaciones/cliente/{clienteId}
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Retona una notificación mediante ID del cliente", description = "Retorna una notificación mediante un ID de cliente proporcionado")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Notificación encontrado"),
                            @ApiResponse(responseCode = "404", description = "Notificación no encontrado"),
                            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<NotificacionDTO>> getByClienteId(@PathVariable Long clienteId) {
        return ResponseEntity.ok(notificacionService.findByClienteId(clienteId));
    }

    // POST /api/v1/notificaciones
    // Endpoint consumido por el microservicio Clientes a través de Feign
    @PostMapping
    @Operation(summary = "Registra una notificación", description = "Registra una notificación en el sistema")
    public ResponseEntity<NotificacionDTO> create(@RequestBody NotificacionRequestDTO request) {
        NotificacionDTO creada = notificacionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    // PUT /api/v1/notificaciones/{id}/leida
    @PutMapping("/{id}/leida")
    @Operation(summary = "Marca una notificación como leida en el sistema")
    public ResponseEntity<NotificacionDTO> marcarComoLeida(@PathVariable Long id) {
        return notificacionService.marcarComoLeida(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/v1/notificaciones/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una notificación", description = "Elimina una notificación del sistema")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return notificacionService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
