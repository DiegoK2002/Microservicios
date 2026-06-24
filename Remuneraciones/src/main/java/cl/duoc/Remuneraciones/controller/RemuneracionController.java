package cl.duoc.Remuneraciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.Remuneraciones.dto.RemuneracionDTO;
import cl.duoc.Remuneraciones.model.Remuneracion;
import cl.duoc.Remuneraciones.service.RemuneracionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/remuneraciones")
@Tag(name = "Remuneraciones", description = "Operaciones sobre las remuneraciones")
public class RemuneracionController {

    @Autowired
    private RemuneracionService service;

    // GET /api/v1/remuneraciones
    @GetMapping
    @Operation(summary = "Retona la lista completa de remuneraciones")
    public ResponseEntity<List<Remuneracion>> listar() {
        List<Remuneracion> lista = service.listarTodas();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // GET /api/v1/remuneraciones/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Retona una remuneración mediante ID", description = "Retorna una remuneración mediante un ID proporcionado")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Remuneración encontrado"),
                            @ApiResponse(responseCode = "404", description = "Remuneración no encontrado"),
                            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Remuneracion> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/v1/remuneraciones/estado/{estado}
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Retona una remuneración mediante su estado", description = "Retorna una remuneración mediante un estado proporcionado")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Remuneración encontrado"),
                            @ApiResponse(responseCode = "404", description = "Remuneración no encontrado"),
                            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Remuneracion>> buscarPorEstado(@PathVariable String estado) {
        try {
            List<Remuneracion> lista = service.buscarPorEstado(estado);
            if (lista.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/v1/remuneraciones/empleado/{nombre}
    @GetMapping("/empleado/{nombre}")
    @Operation(summary = "Retona una remuneración mediante un empleado", description = "Retorna una remuneración mediante un nombre de empleado proporcionado")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Remuneración encontrado"),
                            @ApiResponse(responseCode = "404", description = "Remuneración no encontrado"),
                            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Remuneracion>> buscarPorEmpleado(@PathVariable String nombre) {
        try {
            List<Remuneracion> lista = service.buscarPorNombreEmpleado(nombre);
            if (lista.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/v1/remuneraciones/dto/{id}
    @GetMapping("/dto/{id}")
    @Operation(summary = "Retona una remuneración DTO mediante ID", description = "Retorna una remuneración DTO mediante un ID proporcionado")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Remuneración encontrado"),
                            @ApiResponse(responseCode = "404", description = "Remuneración no encontrado"),
                            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<RemuneracionDTO> obtenerDTO(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.toDTO(service.buscarPorId(id)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/v1/remuneraciones
    @PostMapping
    @Operation(summary = "Registra una remuneración", description = "Registra una remuneración en el sistema")
    public ResponseEntity<?> crear(@RequestBody Remuneracion remuneracion) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(remuneracion));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // PUT /api/v1/remuneraciones/{id}
    @PutMapping("/{id}")
    @Operation(summary = "Actualiza la información de una remuneración")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,
                                                @RequestBody Remuneracion remuneracion) {
        try {
            return ResponseEntity.ok(service.actualizar(id, remuneracion));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // PUT /api/v1/remuneraciones/{id}/pagar
    @PutMapping("/{id}/pagar")
    @Operation(summary = "Paga su remurecación a un empleado")
    public ResponseEntity<?> pagar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.pagar(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // DELETE /api/v1/remuneraciones/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una remuneración", description = "Elimina una remuneración del sistema")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}