package cl.duoc.Remuneraciones.model;

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
public class Remuneracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombreEmpleado;

    @Column(nullable = false)
    private Double salarioBase;

    @Column(nullable = false)
    private Double bonificacion;

    @Column(nullable = false)
    private Double descuentos;

    @Column(nullable = false)
    private String fechaPago;

    @Column(nullable = false)
    private String estado;

    @Column
    private String descripcion;

    // Método para calcular el total
    public Double calcularTotal() {
        return salarioBase + bonificacion - descuentos;
    }
}