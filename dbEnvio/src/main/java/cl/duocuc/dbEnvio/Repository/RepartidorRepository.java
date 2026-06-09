package cl.duocuc.dbEnvio.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duocuc.dbEnvio.Model.Repartidor;

@Repository
public interface RepartidorRepository extends JpaRepository<Repartidor, Integer> {

}