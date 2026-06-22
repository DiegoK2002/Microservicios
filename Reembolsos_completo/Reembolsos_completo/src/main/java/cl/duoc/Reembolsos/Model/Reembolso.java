package cl.duoc.Reembolsos.Model;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Representa un reembolso dentro del sistema")
public class Reembolso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único del reembolso", example = "2")
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "El id de la compra es obligatorio")
    @Schema(description = "Id único de la compra", example = "3")
    private Integer idCompra;

    @Column(nullable = false)
    @NotBlank(message = "El nombre del reembolso es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
    @Schema(description = "Nombre del reembolso")
    private String nombreReembolso;

    @Column(nullable = false)
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor que cero")
    @Schema(description = "Monto de la compra la cual quiere reembolsar", example = "41500")
    private Integer monto;

    @Column(nullable = false)
    @NotNull(message = "La fecha es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Fecha del reembolso", example = "2006-03-24")
    private String fecha;

    @Column(nullable = false)
    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "Pendiente|Aprobado|Rechazado", message = "El estado debe ser Pendiente, Aprobado o Rechazado")
    @Schema(description = "Estado del reembolso", example = "Pendiente")
    private String estado;

    @Column
    @Size(max = 255, message = "La descripcion no puede superar los 255 caracteres")
    @Schema(description = "Explicación de la causa del reembolso")
    private String descripcion;
}
