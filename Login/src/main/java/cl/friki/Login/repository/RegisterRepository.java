package cl.friki.Login.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.friki.Login.model.Register;

@Repository
public interface RegisterRepository extends JpaRepository<Register, Integer>{

    Optional<Register> findById(Integer id);

    Register findByUser(String userName);

}
