package cl.friki.Producto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.friki.Producto.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{

    Optional<Producto> findById(String id);

}
