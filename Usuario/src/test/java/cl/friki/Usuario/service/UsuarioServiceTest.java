package cl.friki.Usuario.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    private UsuarioService service;

    // Datos de prueba
    private Usuario crearUsuario() {
        Direccion direccion = new Direccion(1, "123", "Calle Falsa", "Santiago", "Metropolitana");
        return new Usuario(1, "juan123", "juan@gmail.com", "1234", direccion, 1);
    }

    // listarUsers
    @Test
    void listarUsers_conUsuarios_retornaLista() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(crearUsuario()));

        List<Usuario> resultado = service.listarUsers();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("juan123", resultado.get(0).getNombreUsuario());
    }

    @Test
    void listarUsers_sinUsuarios_retornaListaVacia() {
        when(usuarioRepository.findAll()).thenReturn(Collections.emptyList());

        List<Usuario> resultado = service.listarUsers();

        assertTrue(resultado.isEmpty());
    }

    // buscarPorId
    @Test
    void buscarPorId_existente_retornaUsuario() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(crearUsuario()));

        Usuario resultado = service.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("juan123", resultado.getNombreUsuario());
    }

    @Test
    void buscarPorId_noExistente_lanzaExcepcion() {
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.buscarPorId(99));
    }

    // crearUsuario
    @Test
    void crearUsuario_guardaYRetornaUsuario() {
        Usuario nuevo = crearUsuario();
        when(usuarioRepository.save(nuevo)).thenReturn(nuevo);

        Usuario resultado = service.crearUsuario(nuevo);

        assertNotNull(resultado);
        assertEquals("juan123", resultado.getNombreUsuario());
        verify(usuarioRepository, times(1)).save(nuevo);
    }

    // actualizarUsuario
    @Test
    void actualizarUsuario_existente_retornaActualizado() {
        Usuario anterior = crearUsuario();
        Direccion nuevaDireccion = new Direccion(2, "456", "Calle Nueva", "Valparaiso", "Valparaiso");
        Usuario actualizado = new Usuario(1, "juan_actualizado", "nuevo@gmail.com", "5678", nuevaDireccion, 2);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(anterior));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(actualizado);

        Usuario resultado = service.actualizarUsuario(1, actualizado);

        assertEquals("juan_actualizado", resultado.getNombreUsuario());
        assertEquals("nuevo@gmail.com", resultado.getCorreo());
    }

    @Test
    void actualizarUsuario_noExistente_lanzaExcepcion() {
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
            service.actualizarUsuario(99, crearUsuario())
        );
    }

    // eliminarUsuario
    @Test
    void eliminarUsuario_existente_eliminaCorrectamente() {
        when(usuarioRepository.existsById(1)).thenReturn(true);

        service.eliminarUsuario(1);

        verify(usuarioRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarUsuario_noExistente_lanzaExcepcion() {
        when(usuarioRepository.existsById(99)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> service.eliminarUsuario(99));
    }

    //buscarPorNombreUsuario
    @Test
    void buscarPorNombreUsuario_existente_retornaUsuario() {
        when(usuarioRepository.findByNombreUsuario("juan123"))
            .thenReturn(Optional.of(crearUsuario()));

        Usuario resultado = service.buscarPorNombreUsuario("juan123");

        assertNotNull(resultado);
        assertEquals("juan123", resultado.getNombreUsuario());
    }

    @Test
    void buscarPorNombreUsuario_noExistente_lanzaExcepcion() {
        when(usuarioRepository.findByNombreUsuario("inexistente"))
            .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
            service.buscarPorNombreUsuario("inexistente")
        );
    }
}