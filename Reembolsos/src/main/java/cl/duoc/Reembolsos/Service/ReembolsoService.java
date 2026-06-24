package cl.duoc.Reembolsos.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.Reembolsos.Client.CompraClient;
import cl.duoc.Reembolsos.DTO.CompraDTO;
import cl.duoc.Reembolsos.DTO.ReembolsoDTO;
import cl.duoc.Reembolsos.Exception.ReembolsoNotFoundException;
import cl.duoc.Reembolsos.Model.Reembolso;
import cl.duoc.Reembolsos.Repository.ReembolsoRepository;

@Service
public class ReembolsoService {

    private final ReembolsoRepository reembolsoRepository;
    private final CompraClient compraClient;

    public ReembolsoService(ReembolsoRepository reembolsoRepository, CompraClient compraClient) {
        this.reembolsoRepository = reembolsoRepository;
        this.compraClient = compraClient;
    }

    public List<Reembolso> listarTodos() {
        return reembolsoRepository.findAll();
    }

    public Reembolso buscarPorId(Integer id) {
        return reembolsoRepository.findById(id)
                .orElseThrow(() -> new ReembolsoNotFoundException(id));
    }

    public List<Reembolso> buscarPorEstado(String estado) {
        return reembolsoRepository.findByEstado(estado);
    }

    public Reembolso crear(Reembolso reembolso) {
        CompraDTO compra = compraClient.validarCompraOriginal(reembolso.getIdCompra());
        if (compra == null) {
            throw new RuntimeException("Compra no encontrada");
        }
        reembolso.setId(null);
        return reembolsoRepository.save(reembolso);
    }

    public Reembolso actualizar(Integer id, Reembolso datos) {
        Reembolso existente = buscarPorId(id);
        existente.setNombreReembolso(datos.getNombreReembolso());
        existente.setMonto(datos.getMonto());
        existente.setFecha(datos.getFecha());
        existente.setEstado(datos.getEstado());
        existente.setDescripcion(datos.getDescripcion());
        return reembolsoRepository.save(existente);
    }

    public void eliminar(Integer id) {
        if (!reembolsoRepository.existsById(id)) {
            throw new ReembolsoNotFoundException(id);
        }
        reembolsoRepository.deleteById(id);
    }

    public ReembolsoDTO toDTO(Reembolso r) {
        return new ReembolsoDTO(
                r.getId(),
                r.getNombreReembolso(),
                r.getMonto(),
                r.getFecha(),
                r.getEstado(),
                r.getDescripcion()
        );
    }
}
