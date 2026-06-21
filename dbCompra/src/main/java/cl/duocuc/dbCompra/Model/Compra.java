package cl.duocuc.dbCompra.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "compra")
@Schema(description = "Representa una compra dentro del sistema")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único de la compra", example = "4")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Día de la compra", example = "22")
    private Integer diaCompra;

    @Column(nullable = false)
    @Schema(description = "Mes de la compra", example = "5")
    private Integer mesCompra;

    @Column(nullable = false)
    @Schema(description = "Año de la compra", example = "2014")
    private Integer anoCompra;

    @Column(nullable = false)
    @Schema(description = "Id único del usuario, esta información se trae desde otro microservicio", example = "3")
    private Integer idUsuario;

    @Column(nullable = false)
    @Schema(description = "Id único del producto, esta información se trae desde otro microservicio", example = "1")
    private Integer idProducto;

    @Column(nullable = false)
    @Schema(description = "Id único de la promoción", example = "3")
    private Integer idPromocion;

    @ManyToOne
    @JoinColumn(name = "id_metodo_pago")
    @Schema(description = "Nombre del método de pago", example = "Paypal")
    private MetodoPago metodoPago;
}