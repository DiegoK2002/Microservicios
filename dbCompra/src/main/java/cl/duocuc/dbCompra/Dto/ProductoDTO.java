package cl.duocuc.dbCompra.Dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private Integer id;
    private String nombreProducto;
    private Integer precio;
}