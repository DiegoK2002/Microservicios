package cl.friki.Usuario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa al usuario dentro del sistema")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único del usuario", example = "3")
    private Integer id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Nombre del usuario", example = "Maria")
    private String nombreUsuario;

    @Column(nullable = false, unique = true)
    @Schema(description = "Correo del usuario", example = "ma.fernandez@gmail.com")
    private String correo;
    
    @Column(nullable = false)
    @Schema(description = "Contraseña del usuario", example = "Maria.2000")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @Schema(description = "Datos de dirección del usuario, esto esta en una tabla aparte")
    @JoinColumn(name = "direccion_id", nullable = false)
    private Direccion direccion;

    @Column(name = "rol_id", nullable = false)
    @Schema(description = "Id del rol que tiene el usuario", example = "1")
    private Integer idRol;
}
