package cl.friki.Usuario.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import cl.friki.Usuario.client.RolClient;
import cl.friki.Usuario.dto.RolDTO;
import cl.friki.Usuario.dto.UsuarioDTO;
import cl.friki.Usuario.model.Usuario;
import cl.friki.Usuario.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired                  
    private RolClient rolClient;
    
    @GetMapping
public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
    List<Usuario> listaUsuarios = service.listarUsers();
    
    List<UsuarioDTO> listaDTO = listaUsuarios.stream()
        .map(u -> {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setId(u.getId());
            dto.setNombreUsuario(u.getNombreUsuario());
            
            if (u.getDireccion() != null) {
                dto.setDireccion(u.getDireccion().getCalle());
            }

            try {
                if (u.getIdRol() != null) {
                    RolDTO rol = rolClient.obtenerRol(u.getIdRol());
                    dto.setRol(rol.getNombreRol());
                }
            } catch (Exception e) {
                dto.setRol("Error al obtener rol");
            }
            
            return dto;
        })
        .collect(Collectors.toList());

    if (listaDTO.isEmpty()) {
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(listaDTO);
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
        return ResponseEntity.ok(service.crearUsuario(usuario));
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
            dto.setDireccion(usuario.getDireccion().getCalle());
            RolDTO rol = rolClient.obtenerRol(usuario.getIdRol());
            dto.setRol(rol.getNombreRol());

            return ResponseEntity.ok(dto);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar/{nombreUsuario}")
public ResponseEntity<Usuario> buscarPorNombre(@PathVariable String nombreUsuario) {
    try {
        Usuario usuario = service.buscarPorNombreUsuario(nombreUsuario);
        
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(usuario);
    } catch (Exception e) {
        return ResponseEntity.notFound().build();
    }
}
}
