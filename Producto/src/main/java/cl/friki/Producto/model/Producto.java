package cl.friki.Producto.model;

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
@Table(name = "producto")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa a un producto dentro del sistema")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único del producto", example = "3")
    private Integer id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Nombre del producto", example = "Catán")
    private String nombreProducto;

    @Column(nullable = false)
    @Schema(description = "Cantidad de productos que quedan dentro del sistema", example = "7")
    private Integer cantProducto;

    @Column(nullable = false)
    @Schema(description = "Precio que tiene el producto", example = "15000")
    private Integer precio;

    @Column(nullable = false)
    @Schema(description = "Categoria del producto", example = "Cartas")
    private String tipoProducto;
}