package cl.friki.Login.model;

import cl.friki.Login.dto.DireccionRegisterDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Register {
    private String nombreUsuario; 
    private String correo;
    private String password;
    private DireccionRegisterDTO direccion; 
    private Integer idRol; 
}
