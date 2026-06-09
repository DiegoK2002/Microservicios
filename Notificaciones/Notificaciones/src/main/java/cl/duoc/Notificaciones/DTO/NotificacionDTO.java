package cl.duoc.Notificaciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionDTO {

    private Integer id;
    private String destinatario;
    private String mensaje;
    private String fecha;
    private String estado;
}
