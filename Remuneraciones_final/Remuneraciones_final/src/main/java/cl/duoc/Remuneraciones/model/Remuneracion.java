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
@Schema(description = "Representa una remuneración dentro del sistema")
public class Remuneracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único de la remuneración", example = "3")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre del empleado al que se le va a pagar", example = "Monika")
    private String nombreEmpleado;

    @Column(nullable = false)
    @Schema(description = "Salario que se le va a pagar en la remuneración", example = "560000")
    private Double salarioBase;

    @Column(nullable = false)
    @Schema(description = "Bonificación que se aplicara en la remuneración", example = "189000")
    private Double bonificacion;

    @Column(nullable = false)
    @Schema(description = "Descuento que se resta en la remuneración", example = "57000")
    private Double descuentos;

    @Column(nullable = false)
    @Schema(description = "Fecha de pago de la remuneración", example = "12-03-2015")
    private String fechaPago;

    @Column(nullable = false)
    @Schema(description = "Estado de la remuneración", example = "Pendiente")
    private String estado;

    @Column
    @Schema(description = "Descrioción sobre la remuneración")
    private String descripcion;

    // Método para calcular el total
    public Double calcularTotal() {
        return salarioBase + bonificacion - descuentos;
    }
}