package cl.duocuc.dbResenas.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resenas")
@Schema(description = "Representa una reseña dentro del sistema")
public class Resenas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único de la reseña", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre del producto al cual va la reseña", example = "Paquete de cartas pokemon")
    private String nombreProducto;

    @Column(nullable = false)
    @Schema(description = "Id único del producto de la reseña", example = "2")
    private Integer idProducto;
    
    @Column(nullable = false)
    @Min(value = 1, message = "El puntaje mínimo debe ser 1")
    @Max(value = 5, message = "El puntaje máximo debe ser 5")
    @Schema(description = "Es la cantidad de estrellas o puntaje que se le da al producto dentro de la reseña", example = "4")
    private Integer puntaje;

    @Column(nullable = false, length = 150)
    @Size(max = 150, message = "La descripción no puede superar los 150 caracteres")
    @Schema(description = "Muestra el porque del puntaje o estrellas de la reseña")
    private String descripcion;
}