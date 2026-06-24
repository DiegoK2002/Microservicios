package cl.friki.notificaciones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "notificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa una notificación dentro del sistema")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único de la notificaión", example = "4")
    private Long id;

    // Referencia al cliente que recibe la notificación (microservicio Clientes)
    @Column(nullable = false)
    @Schema(description = "Id único del cliente al que le llegara la notificación", example = "2")
    private Long clienteId;

    // Tipo de notificación: BIENVENIDA, ACTUALIZACION, ELIMINACION, GENERAL...
    @Column(nullable = false)
    @Schema(description = "Tipo de notificación", example = "Actualización")
    private String tipo;

    @Column(nullable = false, length = 500)
    @Schema(description = "Mensaje con el que llega la notificación")
    private String mensaje;

    @Column(nullable = false)
    @Schema(description = "Fecha de envío de la notificación")
    private LocalDateTime fechaEnvio;

    @Column(nullable = false)
    @Schema(description = "Estado de la notificación, se refiere a si fue vista o no")
    private boolean leida;

    /**
     * Constructor de conveniencia usado al crear una notificación nueva.
     * Asigna fecha de envío automática y queda marcada como no leída.
     */
    public Notificacion(Long clienteId, String tipo, String mensaje) {
        this.clienteId = clienteId;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.fechaEnvio = LocalDateTime.now();
        this.leida = false;
    }
}
