package cl.friki.Login.service;

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

import cl.friki.Login.model.Rol;
import cl.friki.Login.repository.RolRepository;

@ExtendWith(MockitoExtension.class)
public class RolServiceTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    private Rol rolEjemplo;

    @BeforeEach
    void setUp() {
        rolEjemplo = new Rol();
        rolEjemplo.setId(1);
        rolEjemplo.setNombreRol("ADMIN");
    }

    // listarRoles
    @Test
    void listar() {
    
        List<Rol> listaFalsa = new ArrayList<>();
        listaFalsa.add(rolEjemplo);
        when(rolRepository.findAll()).thenReturn(listaFalsa);

       
        List<Rol> listaRoles = rolService.listarRoles();

      
        assertEquals(1, listaRoles.size());
        assertEquals("ADMIN", listaRoles.get(0).getNombreRol());
    }

    // buscarPorId
    @Test
    void buscarPorId_encontrado() {
       
        when(rolRepository.findById(1)).thenReturn(Optional.of(rolEjemplo));

       
        Rol resultado = rolService.buscarPorId(1);

  
        assertEquals(1, resultado.getId());
        assertEquals("ADMIN", resultado.getNombreRol());
    }

    @Test
    void buscarPorId_noEncontrado() {
      
        when(rolRepository.findById(99)).thenReturn(Optional.empty());

      
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            rolService.buscarPorId(99);
        });

        assertEquals("Rol no encontrado", error.getMessage());
    }

    // crearRol
    @Test
    void crearRol() {
        // ARRANGE
        when(rolRepository.save(rolEjemplo)).thenReturn(rolEjemplo);

        // ACT
        Rol resultado = rolService.crearRol(rolEjemplo);

        // ASSERT
        assertEquals("ADMIN", resultado.getNombreRol());
        verify(rolRepository, times(1)).save(rolEjemplo);
    }

    // eliminarRol
    @Test
    void eliminarRol_encontrado() {
        // ARRANGE
        when(rolRepository.existsById(1)).thenReturn(true);

        // ACT
        rolService.eliminarRol(1);

        // ASSERT
        verify(rolRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarRol_noEncontrado() {
   
        when(rolRepository.existsById(99)).thenReturn(false);


        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            rolService.eliminarRol(99);
        });

        assertEquals("Rol no encontrado", error.getMessage());
    }
}