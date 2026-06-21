package cl.duocuc.dbCompra.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duocuc.dbCompra.Model.Promociones;

@Repository
public interface PromocionesRepository extends JpaRepository<Promociones, Integer>{
    Optional<Promociones> findById(Integer id);
}
