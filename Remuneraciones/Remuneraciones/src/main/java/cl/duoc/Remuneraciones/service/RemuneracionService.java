package cl.duoc.Remuneraciones.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.Remuneraciones.client.NotificacionClient;
import cl.duoc.Remuneraciones.dto.NotificacionRequestDTO;
import cl.duoc.Remuneraciones.dto.RemuneracionDTO;
import cl.duoc.Remuneraciones.model.Remuneracion;
import cl.duoc.Remuneraciones.repository.RemuneracionRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RemuneracionService {

    @Autowired
    private RemuneracionRepository remuneracionRepository;

    @Autowired
    private NotificacionClient notificacionClient;

    // ── Listar todas ────────────────────────────────────────────────────────
    public List<Remuneracion> listarTodas() {
        return remuneracionRepository.findAll();
    }

    // ── Buscar por ID ────────────────────────────────────────────────────────
    public Remuneracion buscarPorId(Integer id) {
        return remuneracionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Remuneracion no encontrada con id: " + id));
    }

    // ── Buscar por estado ────────────────────────────────────────────────────
    public List<Remuneracion> buscarPorEstado(String estado) {
        List<Remuneracion> resultado = remuneracionRepository.findByEstado(estado);
        if (resultado.isEmpty()) {
            throw new RuntimeException("No hay remuneraciones con estado: " + estado);
        }
        return resultado;
    }

    // ── Buscar por nombre del empleado ───────────────────────────────────────
    public List<Remuneracion> buscarPorNombreEmpleado(String nombre) {
        List<Remuneracion> resultado = remuneracionRepository.findByNombreEmpleadoContainingIgnoreCase(nombre);
        if (resultado.isEmpty()) {
            throw new RuntimeException("No hay remuneraciones para empleado: " + nombre);
        }
        return resultado;
    }

    // ── Crear ───────────────────────────────────────────────────────────────
    public Remuneracion crear(Remuneracion remuneracion) {
        if (remuneracion.getNombreEmpleado() == null || remuneracion.getNombreEmpleado().isEmpty()) {
            throw new RuntimeException("El nombre del empleado es obligatorio");
        }
        if (remuneracion.getSalarioBase() == null || remuneracion.getSalarioBase() <= 0) {
            throw new RuntimeException("El salario base debe ser mayor a 0");
        }
        if (remuneracion.getBonificacion() == null) {
            remuneracion.setBonificacion(0.0);
        }
        if (remuneracion.getDescuentos() == null) {
            remuneracion.setDescuentos(0.0);
        }
        if (remuneracion.getEstado() == null || remuneracion.getEstado().isEmpty()) {
            remuneracion.setEstado("PENDIENTE");
        }
        Remuneracion guardada = remuneracionRepository.save(remuneracion);

        // Notifica la creación (no corta el flujo si Notificaciones está caído)
        enviarNotificacion(
                guardada.getId(),
                "REMUNERACION_CREADA",
                "Se creó la remuneración de " + guardada.getNombreEmpleado()
                        + " por un total de " + guardada.calcularTotal());

        return guardada;
    }

    // ── Pagar (cambia estado a PAGADO y dispara notificación) ────────────────
    public Remuneracion pagar(Integer id) {
        Remuneracion existente = buscarPorId(id);
        existente.setEstado("PAGADO");
        Remuneracion guardada = remuneracionRepository.save(existente);

        enviarNotificacion(
                guardada.getId(),
                "PAGO_PROCESADO",
                "Se procesó el pago de " + guardada.getNombreEmpleado()
                        + " por un total de " + guardada.calcularTotal());

        return guardada;
    }

    // ── Llamada Feign hacia Notificaciones (tolerante a fallos) ──────────────
    private void enviarNotificacion(Integer remuneracionId, String tipo, String mensaje) {
        try {
            // La entidad usa nombreEmpleado (no un id numérico de empleado),
            // por lo que se usa el id de la remuneración como referencia.
            notificacionClient.enviarNotificacion(
                    new NotificacionRequestDTO(remuneracionId.longValue(), tipo, mensaje));
        } catch (Exception e) {
            log.warn("No se pudo enviar la notificación '{}' a Notificaciones: {}", tipo, e.getMessage());
        }
    }

    // ── Actualizar ──────────────────────────────────────────────────────────
    public Remuneracion actualizar(Integer id, Remuneracion datos) {
        Remuneracion existente = buscarPorId(id);
        existente.setNombreEmpleado(datos.getNombreEmpleado());
        existente.setSalarioBase(datos.getSalarioBase());
        existente.setBonificacion(datos.getBonificacion());
        existente.setDescuentos(datos.getDescuentos());
        existente.setFechaPago(datos.getFechaPago());
        existente.setEstado(datos.getEstado());
        existente.setDescripcion(datos.getDescripcion());
        return remuneracionRepository.save(existente);
    }

    // ── Eliminar ────────────────────────────────────────────────────────────
    public void eliminar(Integer id) {
        if (!remuneracionRepository.existsById(id)) {
            throw new RuntimeException("Remuneracion no encontrada con id: " + id);
        }
        remuneracionRepository.deleteById(id);
    }

    // ── Convertir entidad → DTO ─────────────────────────────────────────────
    public RemuneracionDTO toDTO(Remuneracion r) {
        return new RemuneracionDTO(
                r.getId(),
                r.getNombreEmpleado(),
                r.getSalarioBase(),
                r.getBonificacion(),
                r.getDescuentos(),
                r.calcularTotal(),
                r.getFechaPago(),
                r.getEstado(),
                r.getDescripcion()
        );
    }
}