package cl.duocuc.dbEnvio.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDTO {
    private String destinatario;
    private String mensaje;
    private String fecha;
    private String estado;
}