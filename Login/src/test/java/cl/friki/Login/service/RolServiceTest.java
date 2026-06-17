package cl.friki.Login.service;

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

import cl.friki.Login.model.Rol;
import cl.friki.Login.repository.RolRepository;

@ExtendWith(MockitoExtension.class)
public class RolServiceTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService service;

    private Rol crearRol() {
        return new Rol(1, "ADMIN");
    }

    // listarRoles
    @Test
    void listarRoles_conRoles_retornaLista() {
        when(rolRepository.findAll()).thenReturn(Arrays.asList(crearRol()));

        List<Rol> resultado = service.listarRoles();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("ADMIN", resultado.get(0).getNombreRol());
    }

    @Test
    void listarRoles_sinRoles_retornaListaVacia() {
        when(rolRepository.findAll()).thenReturn(Collections.emptyList());

        List<Rol> resultado = service.listarRoles();

        assertTrue(resultado.isEmpty());
    }

    // buscarPorId
    @Test
    void buscarPorId_existente_retornaRol() {
        when(rolRepository.findById(1)).thenReturn(Optional.of(crearRol()));

        Rol resultado = service.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals("ADMIN", resultado.getNombreRol());
    }

    @Test
    void buscarPorId_noExistente_lanzaExcepcion() {
        when(rolRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.buscarPorId(99));
    }

    // crearRol
    @Test
    void crearRol_guardaYRetornaRol() {
        Rol nuevo = crearRol();
        when(rolRepository.save(nuevo)).thenReturn(nuevo);

        Rol resultado = service.crearRol(nuevo);

        assertNotNull(resultado);
        assertEquals("ADMIN", resultado.getNombreRol());
        verify(rolRepository, times(1)).save(nuevo);
    }

    // eliminarRol
    @Test
    void eliminarRol_existente_eliminaCorrectamente() {
        when(rolRepository.existsById(1)).thenReturn(true);

        service.eliminarRol(1);

        verify(rolRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarRol_noExistente_lanzaExcepcion() {
        when(rolRepository.existsById(99)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> service.eliminarRol(99));
    }
}