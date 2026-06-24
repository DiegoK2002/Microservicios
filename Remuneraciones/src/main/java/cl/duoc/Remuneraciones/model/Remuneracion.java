package cl.duoc.Remuneraciones.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "remuneraciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa una remuneración en el sistema")
public class Remuneracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único de la remuneración", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre del empleado a quien va dirigido la remuneración", example = "Manuel")
    private String nombreEmpleado;

    @Column(nullable = false)
    @Schema(description = "Salario base del empleado en la remuneración", example = "650000")
    private Double salarioBase;

    @Column(nullable = false)
    @Schema(description = "Bonificación del empleado en la remuneración", example = "70000")
    private Double bonificacion;

    @Column(nullable = false)
    @Schema(description = "Descuento que le aplican al empleado en la remuneración", example = "35000")
    private Double descuentos;

    @Column(nullable = false)
    @Schema(description = "Fecha del pago de la remuneración", example = "15-06-2025")
    private String fechaPago;

    @Column(nullable = false)
    @Schema(description = "Estado de la remuneración", example = "En proceso")
    private String estado;

    @Column
    @Schema(description = "Descripción de la remuneración")
    private String descripcion;

    // Método para calcular el total
    public Double calcularTotal() {
        return salarioBase + bonificacion - descuentos;
    }
}