package cl.friki.Login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.friki.Login.model.Register;
import cl.friki.Login.repository.RegisterRepository;

@Service
public class RegisterService {

    @Autowired
    private RegisterRepository registerRepository;
    
    //crear usuario
    public Register crearRegister(Register register){
        return registerRepository.save(register);
    }

}
