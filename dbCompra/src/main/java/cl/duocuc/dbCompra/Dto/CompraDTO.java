package cl.duocuc.dbCompra.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraDTO {
    private Integer id;
    private Integer diaCompra;
    private Integer mesCompra;
    private Integer anoCompra;
    private Integer valorTotal;
    private ProductoDTO productoDTO;
    private UsuarioDTO usuarioDTO;
}
