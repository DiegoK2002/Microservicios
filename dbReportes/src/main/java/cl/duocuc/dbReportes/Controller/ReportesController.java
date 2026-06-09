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

@RestController
@RequestMapping("/api/v1/reportes")
public class ReportesController {
    @Autowired
    private ReportesService service;

    @GetMapping
    public ResponseEntity<List<Reportes>> listarReportes(){
        List<Reportes> listaReportes = service.listaReporte();
        if (listaReportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(listaReportes);
        }
    }

    @GetMapping("/dto/{id}")
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
    public ResponseEntity<Reportes> obtenerReportePorId(@PathVariable Integer id) {
        try {
            Reportes reportes = service.buscarPorId(id);
            return ResponseEntity.ok(reportes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Reportes> guardarReporte(@RequestBody Reportes reportes) {
        Reportes nuevoReporte = service.guardarReportes(reportes);
        return ResponseEntity.ok(nuevoReporte);
    }
}
