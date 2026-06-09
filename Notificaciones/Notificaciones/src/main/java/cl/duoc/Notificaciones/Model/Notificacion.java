package cl.duoc.Notificaciones.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notificaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String destinatario;

    @Column(nullable = false, length = 500)
    private String mensaje;

    @Column(nullable = false)
    private String fecha;

    // "Enviada", "Pendiente", "Fallida"
    @Column(nullable = false)
    private String estado;
}
