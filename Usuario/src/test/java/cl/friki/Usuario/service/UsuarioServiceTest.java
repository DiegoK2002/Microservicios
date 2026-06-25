package cl.friki.Usuario.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.friki.Usuario.model.Direccion;
import cl.friki.Usuario.model.Usuario;
import cl.friki.Usuario.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioEjemplo;

    @BeforeEach
    void setUp() {
        usuarioEjemplo = new Usuario();
        usuarioEjemplo.setId(1);
        usuarioEjemplo.setNombreUsuario("juan123");
        usuarioEjemplo.setCorreo("juan@gmail.com");
        usuarioEjemplo.setPassword("1234");
        usuarioEjemplo.setDireccion(new Direccion(1, "123", "Calle Falsa", "Santiago", "Metropolitana"));
        usuarioEjemplo.setIdRol(1);
    }

    // listarUsers
    @Test
    void listar() {
        // ARRANGE
        List<Usuario> listaFalsa = new ArrayList<>();
        listaFalsa.add(usuarioEjemplo);
        when(usuarioRepository.findAll()).thenReturn(listaFalsa);

        List<Usuario> listaUsuarios = usuarioService.listarUsers();

        assertEquals(1, listaUsuarios.size());
        assertEquals("juan123", listaUsuarios.get(0).getNombreUsuario());
    }

    //buscarPorId
    @Test
    void buscarPorId_encontrado() {
        // ARRANGE
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioEjemplo));

        Usuario resultado = usuarioService.buscarPorId(1);

        assertEquals(1, resultado.getId());
        assertEquals("juan123", resultado.getNombreUsuario());
    }

    @Test
    void buscarPorId_noEncontrado() {
        // ARRANGE
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            usuarioService.buscarPorId(99);
        });

        assertEquals("no se encontró a ese usuario", error.getMessage());
    }

    // crearUsuario
    @Test
    void crearUsuario() {

        when(usuarioRepository.save(usuarioEjemplo)).thenReturn(usuarioEjemplo);

      
        Usuario resultado = usuarioService.crearUsuario(usuarioEjemplo);

        assertEquals("juan123", resultado.getNombreUsuario());
        verify(usuarioRepository, times(1)).save(usuarioEjemplo);
    }

    // actualizarUsuario
    @Test
    void actualizarUsuario_encontrado() {
    
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombreUsuario("juan_nuevo");
        usuarioActualizado.setCorreo("nuevo@gmail.com");
        usuarioActualizado.setPassword("5678");
        usuarioActualizado.setDireccion(new Direccion(2, "456", "Calle Nueva", "Valparaiso", "Valparaiso"));

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioEjemplo));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioActualizado);

        Usuario resultado = usuarioService.actualizarUsuario(1, usuarioActualizado);

        assertEquals("juan_nuevo", resultado.getNombreUsuario());
        assertEquals("nuevo@gmail.com", resultado.getCorreo());
    }

    @Test
    void actualizarUsuario_noEncontrado() {
  
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            usuarioService.actualizarUsuario(99, usuarioEjemplo);
        });

        assertEquals("Usuario no encontrado", error.getMessage());
    }

    // eliminarUsuario
    @Test
    void eliminarUsuario_encontrado() {

        when(usuarioRepository.existsById(1)).thenReturn(true);

        usuarioService.eliminarUsuario(1);

        verify(usuarioRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarUsuario_noEncontrado() {

        when(usuarioRepository.existsById(99)).thenReturn(false);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            usuarioService.eliminarUsuario(99);
        });

        assertEquals("Usuario no encontrado", error.getMessage());
    }

    // buscarPorNombreUsuario
    @Test
    void buscarPorNombreUsuario_encontrado() {
     
        when(usuarioRepository.findByNombreUsuario("juan123"))
            .thenReturn(Optional.of(usuarioEjemplo));

    
        Usuario resultado = usuarioService.buscarPorNombreUsuario("juan123");

        assertEquals("juan123", resultado.getNombreUsuario());
    }

    @Test
    void buscarPorNombreUsuario_noEncontrado() {
  
        when(usuarioRepository.findByNombreUsuario("inexistente"))
            .thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            usuarioService.buscarPorNombreUsuario("inexistente");
        });

        assertEquals("Usuario no encontrado", error.getMessage());
    }
}