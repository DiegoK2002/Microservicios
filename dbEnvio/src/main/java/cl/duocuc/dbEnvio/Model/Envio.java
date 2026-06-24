package cl.duocuc.dbEnvio.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "envio")
@Schema(description = "Representa a los envios dentro del sistema")
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único del envio", example = "2")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Id único de la compra", example = "1")
    private Integer idCompra;

    @ManyToOne
    @JoinColumn(name = "repartidor_id")
    @JsonBackReference
    @Schema(description = "Datos del repartidor, esta información se entrega en otra tabla")
    private Repartidor repartidor;
}