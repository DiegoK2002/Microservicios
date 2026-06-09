package cl.duocuc.dbResenas.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResenasDTO {
    private Integer id;
    private String nombreProducto;
    private Integer puntaje;
    private String descripcion;
}
