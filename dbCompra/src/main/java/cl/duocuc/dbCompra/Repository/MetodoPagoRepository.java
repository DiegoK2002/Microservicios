package cl.duocuc.dbCompra.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duocuc.dbCompra.Model.MetodoPago;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {
    Optional<MetodoPago> findById(Integer id);
}
