package cl.friki.Login.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cl.friki.Login.model.Register;
import cl.friki.Login.repository.RegisterRepository;

@Service
public class LoginService {


    @Autowired
    private RegisterRepository registerRepo;

    public ResponseEntity<Register> usuarioLogin(String userName, String password){
        Register usuario = registerRepo.findByUser(userName);

        if(usuario.getPassword()== password){
            return ResponseEntity.ok(usuario);
        }else{
            return ResponseEntity.notFound().build();
        }
    }


}
