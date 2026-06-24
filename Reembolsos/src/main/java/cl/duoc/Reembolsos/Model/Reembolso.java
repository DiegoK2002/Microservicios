package cl.duoc.Reembolsos.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reembolsos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reembolso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "El id de la compra es obligatorio")
    private Integer idCompra;

    @Column(nullable = false)
    @NotBlank(message = "El nombre del reembolso es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
    private String nombreReembolso;

    @Column(nullable = false)
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor que cero")
    private Integer monto;

    @Column(nullable = false)
    @NotNull(message = "La fecha es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String fecha;

    @Column(nullable = false)
    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "Pendiente|Aprobado|Rechazado", message = "El estado debe ser Pendiente, Aprobado o Rechazado")
    private String estado;

    @Column
    @Size(max = 255, message = "La descripcion no puede superar los 255 caracteres")
    private String descripcion;
}
