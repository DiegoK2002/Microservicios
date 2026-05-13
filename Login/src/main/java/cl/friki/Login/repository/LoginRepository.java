package cl.friki.Login.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import cl.friki.Login.model.Login;


@Repository
public interface LoginRepository{

    Optional<Login> findByuserName(String userName);
}
