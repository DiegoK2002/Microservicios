package cl.duocuc.dbReportes.Model;

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
@Table(name = "reportes")
@Schema(description = "Representa a los reportes dentro del sistema")
public class Reportes {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único del reporte", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Id único de la compra que se va a reportar", example = "7")
    private Integer idCompra;
}
