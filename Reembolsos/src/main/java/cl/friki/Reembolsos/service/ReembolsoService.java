package cl.friki.Reembolsos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.friki.Reembolsos.dto.ReembolsoDTO;
import cl.friki.Reembolsos.model.Reembolso;
import cl.friki.Reembolsos.repository.ReembolsoRepository;

@Service
public class ReembolsoService {

    @Autowired
    private ReembolsoRepository reembolsoRepository;

    // ── Listar todos ────────────────────────────────────────────────────────
    public List<Reembolso> listarTodos() {
        return reembolsoRepository.findAll();
    }

    // ── Buscar por ID ────────────────────────────────────────────────────────
    public Reembolso buscarPorId(Integer id) {
        return reembolsoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reembolso no encontrado con id: " + id));
    }

    // ── Buscar por estado ────────────────────────────────────────────────────
    public List<Reembolso> buscarPorEstado(String estado) {
        List<Reembolso> resultado = reembolsoRepository.findByEstado(estado);
        if (resultado.isEmpty()) {
            throw new RuntimeException("No hay reembolsos con estado: " + estado);
        }
        return resultado;
    }

    // ── Buscar por nombre ───────────────────────────────────────────────────
    public List<Reembolso> buscarPorNombre(String nombre) {
        List<Reembolso> resultado = reembolsoRepository.findByNombreReembolsoContainingIgnoreCase(nombre);
        if (resultado.isEmpty()) {
            throw new RuntimeException("No hay reembolsos con nombre: " + nombre);
        }
        return resultado;
    }

    // ── Crear ────────────────────────────────────────────────────────────────
    public Reembolso crear(Reembolso reembolso) {
        if (reembolso.getNombreReembolso() == null || reembolso.getNombreReembolso().isEmpty()) {
            throw new RuntimeException("El nombre del reembolso es obligatorio");
        }
        if (reembolso.getMonto() == null || reembolso.getMonto() <= 0) {
            throw new RuntimeException("El monto debe ser mayor a 0");
        }
        return reembolsoRepository.save(reembolso);
    }

    // ── Actualizar ───────────────────────────────────────────────────────────
    public Reembolso actualizar(Integer id, Reembolso datos) {
        Reembolso existente = buscarPorId(id);
        existente.setNombreReembolso(datos.getNombreReembolso());
        existente.setMonto(datos.getMonto());
        existente.setFecha(datos.getFecha());
        existente.setEstado(datos.getEstado());
        existente.setDescripcion(datos.getDescripcion());
        return reembolsoRepository.save(existente);
    }

    // ── Eliminar ─────────────────────────────────────────────────────────────
    public void eliminar(Integer id) {
        if (!reembolsoRepository.existsById(id)) {
            throw new RuntimeException("Reembolso no encontrado con id: " + id);
        }
        reembolsoRepository.deleteById(id);
    }

    // ── Convertir entidad → DTO ──────────────────────────────────────────────
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