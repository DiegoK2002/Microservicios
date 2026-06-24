package cl.duocuc.dbResenas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duocuc.dbResenas.Model.Resenas;

@Repository
public interface ResenasRepository extends JpaRepository<Resenas, Integer>{

}
