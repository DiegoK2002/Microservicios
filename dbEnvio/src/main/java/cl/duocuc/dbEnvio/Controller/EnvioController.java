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

import cl.duocuc.dbEnvio.Client.CompraClient;
import cl.duocuc.dbEnvio.Dto.CompraDTO;
import cl.duocuc.dbEnvio.Dto.EnvioDTO;
import cl.duocuc.dbEnvio.Model.Envio;
import cl.duocuc.dbEnvio.Service.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/envios")
@Tag(name = "Envios", description = "Operaciones sobre envios")
public class EnvioController {
    @Autowired
    private EnvioService service;

    @Autowired
    private CompraClient compraClient;

    @GetMapping
    @Operation(summary = "Retorna la lista completa de compras")
    public ResponseEntity<List<Envio>> listarEnvios() {
        List<Envio> envios = service.listaEnvios();
        
        if(envios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(envios);
        }
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Retorna un envio mediante el ID", description = "En realidad retorna un envio DTO pero en ambos casos muestra todo lo de la compra")
    public ResponseEntity<EnvioDTO> buscarDTO(@PathVariable Integer id) {
        try {
            Envio envio = service.buscarPorId(id);
            CompraDTO compra = compraClient.obtenerCompraPorId(envio.getId());

            EnvioDTO envioDTO = new EnvioDTO();
            envioDTO.setId(envio.getId());
            envioDTO.setCompraDTO(compra);
            if (envio.getRepartidor() != null) {
            envioDTO.setNombreRepartidor(envio.getRepartidor().getNombre());
            }
            return ResponseEntity.ok(envioDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Registra un envio en el sistema")
    public ResponseEntity<Envio> guardarEnvio(@RequestBody Envio envio) {
        try {
            return ResponseEntity.ok(service.guardarEnvio(envio));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}