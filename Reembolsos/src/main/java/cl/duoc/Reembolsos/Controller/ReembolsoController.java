package cl.duoc.Reembolsos.Controller;

import cl.duoc.Reembolsos.DTO.ReembolsoDTO;
import cl.duoc.Reembolsos.Model.Reembolso;
import cl.duoc.Reembolsos.Service.ReembolsoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reembolsos")
@Tag(name = "Reembolsos", description = "Operaciones sobre los reembolsos")
public class ReembolsoController {

    private final ReembolsoService service;

    public ReembolsoController(ReembolsoService service) {
        this.service = service;
    }

    // GET /api/v1/reembolsos
    @GetMapping
    @Operation(summary = "Retona la lista completa de reembolsos")
    public ResponseEntity<List<Reembolso>> listar() {
        List<Reembolso> lista = service.listarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // GET /api/v1/reembolsos/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Busca un reembolso por ID", description = "Retorna un reembolso mediante un ID proporcionado")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Reembolso encontrado"),
                            @ApiResponse(responseCode = "404", description = "Reembolso no encontrado"),
                            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Reembolso> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // GET /api/v1/reembolsos/estado/{estado}
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Busca un reembolso por su estado", description = "Retorna un reembolso mediante un estado proporcionado")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Reembolso encontrado"),
                            @ApiResponse(responseCode = "404", description = "Reembolso no encontrado"),
                            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Reembolso>> buscarPorEstado(@PathVariable String estado) {
        List<Reembolso> lista = service.buscarPorEstado(estado);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // GET /api/v1/reembolsos/dto/{id}
    @GetMapping("/dto/{id}")
    @Operation(summary = "Busca un reembolso DTO por ID", description = "Retorna un reembolso DTO mediante un ID proporcionado")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Reembolso encontrado"),
                            @ApiResponse(responseCode = "404", description = "Reembolso no encontrado"),
                            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ReembolsoDTO> obtenerDTO(@PathVariable Integer id) {
        return ResponseEntity.ok(service.toDTO(service.buscarPorId(id)));
    }

    // POST /api/v1/reembolsos
    @PostMapping
    @Operation(summary = "Registra un reembolso", description = "Registra un reembolso en el sistema")
    public ResponseEntity<Reembolso> crear(@Valid @RequestBody Reembolso reembolso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(reembolso));
    }

    // PUT /api/v1/reembolsos/{id}
    @PutMapping("/{id}")
    @Operation(summary = "Actualiza los datos de un reembolso")
    public ResponseEntity<Reembolso> actualizar(@PathVariable Integer id,
                                                @Valid @RequestBody Reembolso reembolso) {
        return ResponseEntity.ok(service.actualizar(id, reembolso));
    }

    // DELETE /api/v1/reembolsos/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un reembolso", description = "Elimina un reembolso del sistema mediante su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
