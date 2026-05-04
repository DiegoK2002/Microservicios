package cl.friki.Usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.friki.Usuario.dto.UsuarioDTO;
import cl.friki.Usuario.model.Usuario;
import cl.friki.Usuario.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        
        List<Usuario> listaUsuarios = service.listarUsers();

        if(listaUsuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaUsuarios);
        }
    }

    //buscar usuario por id
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id){
        try{
            Usuario usuario = service.buscarPorId(id);
            return ResponseEntity.ok(usuario);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    //crear usuario nuevo
    @PostMapping
    public ResponseEntity<Usuario> guardar(@RequestBody Usuario usuario){
        return ResponseEntity.ok(service.agregarUsuario(usuario));
    }

    //eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        try{
            service.eliminarUsuario(id);
            return ResponseEntity.noContent().build();
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    //actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Integer id, @RequestBody Usuario usuario){
        try{
            return ResponseEntity.ok(service.actualizarUsuario(id, usuario));
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    //buscar dto por id
    @GetMapping("/dto/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioDTO(@PathVariable Integer id){
        try{
            Usuario usuario = service.buscarPorId(id);

            UsuarioDTO dto = new UsuarioDTO();

            dto.setId(usuario.getId());
            dto.setNombreUsuario(usuario.getNombreUsuario());
            dto.setRol(usuario.getRol());
            dto.setDireccion(usuario.getDireccion().getCalle());

            return ResponseEntity.ok(dto);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
