package cl.duoc.Reembolsos.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReembolsoDTO {

    private Integer id;
    private String nombreReembolso;
    private Integer monto;
    private String fecha;
    private String estado;
    private String descripcion;
}
