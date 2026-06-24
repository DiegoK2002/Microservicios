package cl.friki.notificaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para crear una notificación.
 * Este es el "contrato" que el microservicio Clientes envía vía Feign.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionRequestDTO {

    private Long clienteId;
    private String tipo;
    private String mensaje;
}
