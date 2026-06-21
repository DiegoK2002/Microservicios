package cl.duocuc.dbCompra.Model;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "promociones")
@Schema(description = "Representa una promoción dentro del sistema")
public class Promociones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único de la promoción", example = "3")
    private Integer id;
    
    @Column(nullable = false)
    @Schema(description = "Porcentaje de descuento", example = "0.70")
    private double porcentaje;

    @Column(nullable = false)
    @Schema(description = "Cantidad que representa el descuento", example = "30%")
    private String cantidad;
}