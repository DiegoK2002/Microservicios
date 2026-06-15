package cl.duocuc.dbReportes.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duocuc.dbReportes.Client.CompraClient;
import cl.duocuc.dbReportes.Dto.CompraDTO;
import cl.duocuc.dbReportes.Dto.ReportesDTO;
import cl.duocuc.dbReportes.Model.Reportes;
import cl.duocuc.dbReportes.Service.ReportesService;
import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/reportes")
@Tag(name = "Opereaciones sobre reportes")
public class ReportesController {
    @Autowired
    private ReportesService service;

    @Autowired
    private CompraClient compraClient;

    @GetMapping
    @Operation(summary = "Retorna la lista completa de reportes")
    public ResponseEntity<List<Reportes>> listarReportes(){
        List<Reportes> listaReportes = service.listaReporte();
        if (listaReportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(listaReportes);
        }
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Retorna un reporte segun el ID proporcionado", description = "Retorna el DTO de reportes enriquecido con la información de la compra")
    public ResponseEntity<ReportesDTO> buscarDTO(@PathVariable Integer id) {
        try {
            Reportes reportes = service.buscarPorId(id);
            if (reportes == null) {
                return ResponseEntity.notFound().build();
            }

            CompraDTO compra = compraClient.obtenerCompraPorId(reportes.getIdCompra());

            ReportesDTO reportesDTO = new ReportesDTO();
            reportesDTO.setId(reportes.getId());
            reportesDTO.setCompraDTO(compra);

            return ResponseEntity.ok(reportesDTO);
        } catch (FeignException.NotFound e) {
        // Si el reporte existe pero la compra fue eliminada o no existe
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
        // Error genérico de servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/mes/{mes}")
    @Operation(summary = "Retorna reportes filtrados por mes")
    public ResponseEntity<List<ReportesDTO>> buscarPorMes(@PathVariable Integer mes) { // <- Corregido aquí
        try {
        // 1. Le pides al microservicio de compras que te dé solo las compras de ese mes
            List<CompraDTO> compras = compraClient.obtenerCompraPorMes(mes);
        
            List<ReportesDTO> resultado = new ArrayList<>();

        // 2. Por cada compra encontrada, buscas su reporte local y armas el DTO
            for (CompraDTO compra : compras) {
                Reportes reporte = service.buscarPorIdCompra(compra.getId()); 
            
                if (reporte != null) {
                    ReportesDTO dto = new ReportesDTO();
                    dto.setId(reporte.getId());
                    dto.setCompraDTO(compra); 
                    resultado.add(dto);
                }
            }

            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/año/{ano}")
    @Operation(summary = "Retorna reportes filtrados por mes")
    public ResponseEntity<List<ReportesDTO>> buscarPorAño(@PathVariable Integer ano) { // <- Corregido aquí
        try {
        // 1. Le pides al microservicio de compras que te dé solo las compras de ese mes
            List<CompraDTO> compras = compraClient.obtenerCompraPorAño(ano);
        
            List<ReportesDTO> resultado = new ArrayList<>();

        // 2. Por cada compra encontrada, buscas su reporte local y armas el DTO
            for (CompraDTO compra : compras) {
                Reportes reporte = service.buscarPorIdCompra(compra.getId()); 
            
                if (reporte != null) {
                    ReportesDTO dto = new ReportesDTO();
                    dto.setId(reporte.getId());
                    dto.setCompraDTO(compra); 
                    resultado.add(dto);
                }
            }

            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @Operation(summary = "Registra un nuevo reporte", description = "Registra un reporte en el sistema")
    public ResponseEntity<Reportes> guardarReporte(@RequestBody Reportes reportes) {
        Reportes nuevoReporte = service.guardarReportes(reportes);
        return ResponseEntity.ok(nuevoReporte);
    }
}