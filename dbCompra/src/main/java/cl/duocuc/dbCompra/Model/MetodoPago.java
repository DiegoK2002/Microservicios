package cl.duocuc.dbCompra.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "metodopago")
@Schema(description = "Representa el método de pago dentro del sistema")
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único del método de pago", example = "3")
    private Integer id;
    
    @Column(nullable = false)
    @Schema(description = "Nombre el método de pago", example = "Paypal")
    private String tipoMetodo;

    @OneToMany(mappedBy = "metodoPago")
    @JsonIgnore
    private List<Compra> compras;
}