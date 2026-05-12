package cl.friki.Reembolsos.controller;

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

import cl.friki.Reembolsos.dto.ReembolsoDTO;
import cl.friki.Reembolsos.model.Reembolso;
import cl.friki.Reembolsos.service.ReembolsoService;

@RestController
@RequestMapping("/api/v1/reembolsos")
public class ReembolsoController {

    @Autowired
    private ReembolsoService service;

    // GET /api/v1/reembolsos
    @GetMapping
    public ResponseEntity<List<Reembolso>> listar() {
        List<Reembolso> lista = service.listarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // GET /api/v1/reembolsos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Reembolso> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/v1/reembolsos/estado/{estado}
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Reembolso>> buscarPorEstado(@PathVariable String estado) {
        try {
            List<Reembolso> lista = service.buscarPorEstado(estado);
            if (lista.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/v1/reembolsos/nombre/{nombre}
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Reembolso>> buscarPorNombre(@PathVariable String nombre) {
        try {
            List<Reembolso> lista = service.buscarPorNombre(nombre);
            if (lista.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/v1/reembolsos/dto/{id}
    @GetMapping("/dto/{id}")
    public ResponseEntity<ReembolsoDTO> obtenerDTO(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.toDTO(service.buscarPorId(id)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/v1/reembolsos
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Reembolso reembolso) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(reembolso));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // PUT /api/v1/reembolsos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,
                                                @RequestBody Reembolso reembolso) {
        try {
            return ResponseEntity.ok(service.actualizar(id, reembolso));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // DELETE /api/v1/reembolsos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}