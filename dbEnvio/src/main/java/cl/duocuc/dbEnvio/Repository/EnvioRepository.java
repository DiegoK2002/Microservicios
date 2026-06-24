package cl.duocuc.dbEnvio.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duocuc.dbEnvio.Model.Envio;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Integer> {

}