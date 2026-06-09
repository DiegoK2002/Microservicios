package cl.duocuc.dbResenas.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duocuc.dbResenas.Dto.ResenasDTO;
import cl.duocuc.dbResenas.Model.Resenas;
import cl.duocuc.dbResenas.Service.ResenasService;

@RestController
@RequestMapping("/api/v1/resenas")
public class ResenasController {
    @Autowired
    private ResenasService service;

    @GetMapping
    public ResponseEntity<List<Resenas>> listarReseñas(){
        List<Resenas> listaReseñas = service.listaReseñas();
        if (listaReseñas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(listaReseñas);
        }
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<ResenasDTO> buscarDTO(@PathVariable Integer id) {
        try {
            Resenas resenas = service.buscarPorId(id);
            ResenasDTO resenasDTO = new ResenasDTO();
            resenasDTO.setId(resenas.getId());
            resenasDTO.setNombreProducto(resenas.getNombreProducto());
            resenasDTO.setPuntaje(resenas.getPuntaje());
            resenasDTO.setDescripcion(resenas.getDescripcion());

            return ResponseEntity.ok(resenasDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resenas> obtenerResenaPorId(@PathVariable Integer id) {
        try {
            Resenas resenas = service.buscarPorId(id);
            return ResponseEntity.ok(resenas);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Resenas> guardarResena(@RequestBody Resenas resena) {
        Resenas nuevoResena = service.guardarResenas(resena);
        return ResponseEntity.ok(nuevoResena);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Integer id) {
        try {
            service.eliminarResena(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
