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
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/remuneraciones")
@Tag(name = "Operaciones sobre remuneraciones")
public class RemuneracionController {

    @Autowired
    private RemuneracionService service;

    // GET /api/v1/remuneraciones
    @GetMapping
    @Operation(summary = "Retorna la lista completa de remuneraciones")
    public ResponseEntity<List<Remuneracion>> listar() {
        List<Remuneracion> lista = service.listarTodas();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // GET /api/v1/remuneraciones/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Retorna una remuneración mediante un ID", description = "Retorna una remuneración mediante un ID proporcionado")
    public ResponseEntity<Remuneracion> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/v1/remuneraciones/estado/{estado}
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Retorna una remuneración mediante un estado", description = "Retorna una remuneración mediante un estado proporcionado")
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
    @Operation(summary = "Retorna una remuneración mediante el empleado", description = "Retorna uan remuneración mediante el empleado proporcionado")
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
    @Operation(summary = "Retorna una remuneración DTO segun el ID proporcionado", description = "Metodo que permite retonar una remuneración DTO, normalmente se usa cuando otro microservicio del sistema necesita datos de una remuneración")
    public ResponseEntity<RemuneracionDTO> obtenerDTO(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.toDTO(service.buscarPorId(id)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/v1/remuneraciones
    @PostMapping
    @Operation(summary = "Registra una nueva remuneración", description = "Registra en el sistema una remuneración")
    public ResponseEntity<?> crear(@RequestBody Remuneracion remuneracion) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(remuneracion));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // PUT /api/v1/remuneraciones/{id}
    @PutMapping("/{id}")
    @Operation(summary = "Actualiza la información de una remuneración", description = "Cambia los datos de una remuneración mediate un ID proporcionado")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,
                                                @RequestBody Remuneracion remuneracion) {
        try {
            return ResponseEntity.ok(service.actualizar(id, remuneracion));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // DELETE /api/v1/remuneraciones/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una remuneración mediante in ID", description = "Elimina una remuneración del sistema mediante un ID proporcionado")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}