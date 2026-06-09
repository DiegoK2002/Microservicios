package cl.duocuc.dbReportes.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duocuc.dbReportes.Model.Reportes;

@Repository
public interface ReportesRepository extends JpaRepository<Reportes, Integer> {
    Optional<Reportes> findById(Integer id);
}
