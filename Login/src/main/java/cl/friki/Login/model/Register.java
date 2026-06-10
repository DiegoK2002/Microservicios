package cl.friki.Login.model;

import cl.friki.Login.dto.DireccionRegisterDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa el apartado en donde se crea una nueva cuenta dentro del sistema")
public class Register {
    @Schema(description = "Nombre del usuario", example = "Jorge")
    private String nombreUsuario; 
    @Schema(description = "Correo del usuario", example = "jor.vergara@gmail.com")
    private String correo;
    @Schema(description = "Contraseña del usuario", example = "gomarojanosemoja")
    private String password;
    @Schema(description = "Direción o lugar en donde vive el usuario")
    private DireccionRegisterDTO direccion; 
    @Schema(description = "Número que identifica que tipo de rol tiene el usuario", example = "2")
    private Integer idRol; 
}
