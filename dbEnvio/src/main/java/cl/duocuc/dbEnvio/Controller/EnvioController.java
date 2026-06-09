package cl.duocuc.dbEnvio.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duocuc.dbEnvio.Dto.EnvioDTO;
import cl.duocuc.dbEnvio.Model.Envio;
import cl.duocuc.dbEnvio.Service.EnvioService;

@RestController
@RequestMapping("/api/v1/envios")
public class EnvioController {
    @Autowired
    private EnvioService service;

    @GetMapping
    public ResponseEntity<List<Envio>> listarEnvios() {
        List<Envio> envios = service.listaEnvios();
        
        if(envios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(envios);
        }
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<EnvioDTO> buscarDTO(@PathVariable Integer id) {
        try {
            Envio envio = service.buscarPorId(id);
            EnvioDTO envioDTO = new EnvioDTO();
            envioDTO.setId(envio.getId());
            if (envio.getRepartidor() != null) {
            envioDTO.setNombreRepartidor(envio.getRepartidor().getNombre());
            }
            return ResponseEntity.ok(envioDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Envio> guardarEnvio(@RequestBody Envio envio) {
    try {
        return ResponseEntity.ok(service.guardarEnvio(envio));
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().build();
    }
}

    @GetMapping("/id/{id}")
    public ResponseEntity<Envio> buscarPorId(@PathVariable Integer id) {
        try {
            Envio envio = service.buscarPorId(id);
            return ResponseEntity.ok(envio);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}