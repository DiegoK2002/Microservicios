package cl.duoc.Notificaciones.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "notificaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa a las notificaciones dentro del sistema")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único de la notificación", example = "2")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre de quien recibe la notificación", example = "Anderson")
    private String destinatario;

    @Column(nullable = false, length = 500)
    @Schema(description = "Mensaje que tendrá la notificación")
    private String mensaje;

    @Column(nullable = false)
    @Schema(description = "Fecha de envio de la notificación", example = "15-06-2018")
    private String fecha;

    // "Enviada", "Pendiente", "Fallida"
    @Column(nullable = false)
    @Schema(description = "Estado de la notificación", example = "Fallida")
    private String estado;
}
