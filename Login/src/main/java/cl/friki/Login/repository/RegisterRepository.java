package cl.friki.Login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.friki.Login.model.Register;

@Repository
public interface RegisterRepository extends JpaRepository<Register, Integer> {

    Optional<Register> findByUserName(String userName);

    Optional<Register> findByCorreo(String correo);
}
