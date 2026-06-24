package cl.duocuc.dbEnvio.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompraDTO {
    private Integer id;
    private Integer diaCompra;
    private Integer mesCompra;
    private Integer anoCompra;
    private Integer valorTotal;
    private ProductoDTO productoDTO;
    private UsuarioDTO usuarioDTO;
}