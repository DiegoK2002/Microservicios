package cl.friki.notificaciones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Referencia al cliente que recibe la notificación (microservicio Clientes)
    @Column(nullable = false)
    private Long clienteId;

    // Tipo de notificación: BIENVENIDA, ACTUALIZACION, ELIMINACION, GENERAL...
    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false, length = 500)
    private String mensaje;

    @Column(nullable = false)
    private LocalDateTime fechaEnvio;

    @Column(nullable = false)
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
