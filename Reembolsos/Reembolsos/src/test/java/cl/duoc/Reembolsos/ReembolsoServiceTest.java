package cl.duoc.Reembolsos;

import cl.duoc.Reembolsos.Client.CompraClient;
import cl.duoc.Reembolsos.DTO.CompraDTO;
import cl.duoc.Reembolsos.Exception.ReembolsoNotFoundException;
import cl.duoc.Reembolsos.Model.Reembolso;
import cl.duoc.Reembolsos.Repository.ReembolsoRepository;
import cl.duoc.Reembolsos.Service.ReembolsoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Reembolso Service Tests")
class ReembolsoServiceTest {

    @Mock
    private ReembolsoRepository reembolsoRepository;

    @Mock
    private CompraClient compraClient;

    @InjectMocks
    private ReembolsoService reembolsoService;

    private Reembolso crearReembolso(Integer id, String nombre, Integer monto, String estado) {
        Reembolso r = new Reembolso();
        r.setId(id);
        r.setIdCompra(1);
        r.setNombreReembolso(nombre);
        r.setMonto(monto);
        r.setFecha("2024-01-15");
        r.setEstado(estado);
        r.setDescripcion("Descripción");
        return r;
    }

    @Test
    void listarTodos_retornaLista() {
        when(reembolsoRepository.findAll()).thenReturn(Arrays.asList(crearReembolso(1, "R1", 10000, "Pendiente")));

        List<Reembolso> resultado = reembolsoService.listarTodos();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getEstado()).isEqualTo("Pendiente");
    }

    @Test
    void buscarPorId_retornaReembolso() {
        Reembolso reembolso = crearReembolso(1, "R1", 10000, "Pendiente");
        when(reembolsoRepository.findById(1)).thenReturn(Optional.of(reembolso));

        Reembolso encontrado = reembolsoService.buscarPorId(1);

        assertThat(encontrado.getNombreReembolso()).isEqualTo("R1");
        verify(reembolsoRepository).findById(1);
    }

    @Test
    void buscarPorId_noExiste_lanzaReembolsoNotFoundException() {
        when(reembolsoRepository.findById(999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reembolsoService.buscarPorId(999))
                .isInstanceOf(ReembolsoNotFoundException.class)
                .hasMessageContaining("999");
    }

    @Test
    void buscarPorEstado_retornaFiltrados() {
        when(reembolsoRepository.findByEstado("Aprobado")).thenReturn(
                Arrays.asList(crearReembolso(1, "R1", 20000, "Aprobado")));

        List<Reembolso> aprobados = reembolsoService.buscarPorEstado("Aprobado");

        assertThat(aprobados).hasSize(1);
        assertThat(aprobados.get(0).getEstado()).isEqualTo("Aprobado");
    }

    @Test
    void crear_compraValida_guardaYRetorna() {
        Reembolso reembolso = crearReembolso(null, "Nuevo", 15000, "Pendiente");
        CompraDTO compraDTO = new CompraDTO(1, null, 15000);
        when(compraClient.validarCompraOriginal(1)).thenReturn(compraDTO);
        when(reembolsoRepository.save(any(Reembolso.class))).thenReturn(reembolso);

        Reembolso creado = reembolsoService.crear(reembolso);

        assertThat(creado.getNombreReembolso()).isEqualTo("Nuevo");
        verify(reembolsoRepository).save(reembolso);
        verify(compraClient).validarCompraOriginal(1);
    }

    @Test
    void crear_compraNoEncontrada_lanzaRuntimeException() {
        Reembolso reembolso = crearReembolso(null, "Nuevo", 15000, "Pendiente");
        when(compraClient.validarCompraOriginal(1)).thenReturn(null);

        assertThatThrownBy(() -> reembolsoService.crear(reembolso))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Compra no encontrada");
    }

    @Test
    void actualizar_existente_retornaActualizado() {
        Reembolso existente = crearReembolso(1, "Original", 10000, "Pendiente");
        Reembolso datos = crearReembolso(null, "Actualizado", 20000, "Aprobado");
        when(reembolsoRepository.findById(1)).thenReturn(Optional.of(existente));
        when(reembolsoRepository.save(any(Reembolso.class))).thenAnswer(inv -> inv.getArgument(0));

        Reembolso resultado = reembolsoService.actualizar(1, datos);

        assertThat(resultado.getNombreReembolso()).isEqualTo("Actualizado");
        assertThat(resultado.getEstado()).isEqualTo("Aprobado");
        assertThat(resultado.getMonto()).isEqualTo(20000);
    }

    @Test
    void eliminar_existente_llamaDelete() {
        when(reembolsoRepository.existsById(1)).thenReturn(true);
        doNothing().when(reembolsoRepository).deleteById(1);

        reembolsoService.eliminar(1);

        verify(reembolsoRepository).existsById(1);
        verify(reembolsoRepository).deleteById(1);
    }

    @Test
    void eliminar_noExiste_lanzaReembolsoNotFoundException() {
        when(reembolsoRepository.existsById(999)).thenReturn(false);

        assertThatThrownBy(() -> reembolsoService.eliminar(999))
                .isInstanceOf(ReembolsoNotFoundException.class);

        verify(reembolsoRepository, never()).deleteById(any());
    }
}
