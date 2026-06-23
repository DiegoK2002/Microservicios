package cl.duoc.Reembolsos.Controller;

import cl.duoc.Reembolsos.DTO.ReembolsoDTO;
import cl.duoc.Reembolsos.Model.Reembolso;
import cl.duoc.Reembolsos.Service.ReembolsoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reembolsos")
public class ReembolsoController {

    private final ReembolsoService service;

    public ReembolsoController(ReembolsoService service) {
        this.service = service;
    }

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
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // GET /api/v1/reembolsos/estado/{estado}
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Reembolso>> buscarPorEstado(@PathVariable String estado) {
        List<Reembolso> lista = service.buscarPorEstado(estado);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // GET /api/v1/reembolsos/dto/{id}
    @GetMapping("/dto/{id}")
    public ResponseEntity<ReembolsoDTO> obtenerDTO(@PathVariable Integer id) {
        return ResponseEntity.ok(service.toDTO(service.buscarPorId(id)));
    }

    // POST /api/v1/reembolsos
    @PostMapping
    public ResponseEntity<Reembolso> crear(@Valid @RequestBody Reembolso reembolso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(reembolso));
    }

    // PUT /api/v1/reembolsos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Reembolso> actualizar(@PathVariable Integer id,
                                                @Valid @RequestBody Reembolso reembolso) {
        return ResponseEntity.ok(service.actualizar(id, reembolso));
    }

    // DELETE /api/v1/reembolsos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
