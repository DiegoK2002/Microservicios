package cl.duocuc.dbReportes.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duocuc.dbReportes.Dto.ReportesDTO;
import cl.duocuc.dbReportes.Model.Reportes;
import cl.duocuc.dbReportes.Service.ReportesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/reportes")
@Tag(name = "Opereaciones sobre reportes")
public class ReportesController {
    @Autowired
    private ReportesService service;

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

    @GetMapping("/dto/{id}")
    @Operation(summary = "Retorna un reporte DTO segun el ID proporcionado", description = "Metodo que permite retonar un reporte DTO, normalmente se usa cuando otro microservicio del sistema necesita datos de un reporte")
    public ResponseEntity<ReportesDTO> buscarDTO(@PathVariable Integer id) {
        try {
            Reportes reportes = service.buscarPorId(id);
            ReportesDTO reportesDTO = new ReportesDTO();
            reportesDTO.setId(reportes.getId());
            reportesDTO.setVentasXMes(reportes.getVentasXMes());
            reportesDTO.setVentasTotales(reportes.getVentasTotales());

            return ResponseEntity.ok(reportesDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna un reporte mediante ID", description = "Retorna un reporte mediante un ID proporcionado")
    public ResponseEntity<Reportes> obtenerReportePorId(@PathVariable Integer id) {
        try {
            Reportes reportes = service.buscarPorId(id);
            return ResponseEntity.ok(reportes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Registra un nuevo reporte", description = "Registra un reporte en el sistema")
    public ResponseEntity<Reportes> guardarReporte(@RequestBody Reportes reportes) {
        Reportes nuevoReporte = service.guardarReportes(reportes);
        return ResponseEntity.ok(nuevoReporte);
    }
}