package cl.friki.notificaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDTO {

    private Long id;
    private Long clienteId;
    private String tipo;
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private boolean leida;
}
