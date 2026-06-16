package cl.friki.clientes.repository;

import cl.friki.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByRut(String rut);

    boolean existsByEmail(String email);

    boolean existsByRut(String rut);
}
