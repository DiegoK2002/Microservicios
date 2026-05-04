package cl.friki.Usuario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.friki.Usuario.model.Usuario;
import cl.friki.Usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //buscar a todos los usuarios
    public List<Usuario> listarUsers(){
        return usuarioRepository.findAll();
    }

    //buscar usuario por id
    public Usuario buscarPorId(Integer id){
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("no se encontró a ese usuario"));
    }
    
    //crear usuario
    public Usuario agregarUsuario(Usuario usuario){
        if(usuario.getDireccion()!= null){
            usuario.getDireccion().setUsuario(usuario);
        }

        return usuarioRepository.save(usuario);
    }

    //actualizar un usuario
    public Usuario actualizarUsuario(Integer id, Usuario usuarioActualizado){
        Usuario usuarioAnt = usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
        
        usuarioAnt.setNombreUsuario(usuarioActualizado.getNombreUsuario());
        usuarioAnt.setPassword(usuarioActualizado.getPassword());
        usuarioAnt.setCorreo(usuarioActualizado.getCorreo());
        usuarioAnt.setRol(usuarioActualizado.getRol());

        if(usuarioActualizado.getDireccion()!=null){
            usuarioActualizado.getDireccion().setUsuario(usuarioAnt);
            usuarioAnt.setDireccion(usuarioActualizado.getDireccion());
        }

        return usuarioRepository.save(usuarioAnt);
    }

    //eliminar un usuario
    public void eliminarUsuario(Integer id){
        if(usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);
        }else{
            
            throw new RuntimeException("Usuario no encontrado");
        }
    }


}
