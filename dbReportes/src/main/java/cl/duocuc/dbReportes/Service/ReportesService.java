package cl.duocuc.dbReportes.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duocuc.dbReportes.Model.Reportes;
import cl.duocuc.dbReportes.Repository.ReportesRepository;

@Service
public class ReportesService {
    @Autowired
    private ReportesRepository repo;
    
    public List<Reportes> listaReporte(){
        return repo.findAll();
    }

    public Reportes buscarPorId(Integer id){
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
    }

    public Reportes guardarReportes(Reportes reporte){
        return repo.save(reporte);
    }
}
