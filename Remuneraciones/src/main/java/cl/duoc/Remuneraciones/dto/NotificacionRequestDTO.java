package cl.duoc.Remuneraciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contrato Feign hacia notificaciones-service.
 * Debe coincidir con el NotificacionRequestDTO del microservicio Notificaciones
 * (cl.friki.notificaciones): clienteId, tipo, mensaje.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionRequestDTO {

    private Long clienteId;
    private String tipo;
    private String mensaje;
}
