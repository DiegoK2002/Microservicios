package cl.friki.Login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.friki.Login.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

}
