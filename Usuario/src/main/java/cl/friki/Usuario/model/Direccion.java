package cl.friki.Usuario.model;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "direccion")
@Schema(description = "Representa la dirección de un usuario dentro del sistema")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único de la dirección", example = "2")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Número de vivienda del usuario", example = "68")
    private String numVivienda;

    @Column(nullable = false)
    @Schema(description = "Número de la calle del usuario", example = "5")
    private String calle;

    @Column(nullable = false)
    @Schema(description = "Nombre de la ciudad del usuario", example = "Santiago")
    private String ciudad;

    @Column(nullable = false)
    @Schema(description = "Nombre de la región del usuario", example = "Metropolitana")
    private String region;
}