package cl.duocuc.dbEnvio.Dto;

import lombok.Data;
import java.util.Date;

@Data
public class CompraDTO {
    private Integer id;
    private Date fechaCompra;
    private Integer valorTotal;
}