package cl.duocuc.dbEnvio.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "repartidor")
@Schema(description = "Representa al repartidor dentro del sistema")
public class Repartidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único del repartidor", example = "2")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre del repartidor", example = "Macarena")
    private String nombre;

    @OneToMany(mappedBy = "repartidor", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Envio> envio;

    public Repartidor(Integer id, String nombre) {
    this.id = id;
    this.nombre = nombre;
    }
}