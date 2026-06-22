package cl.duocuc.dbEnvio.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioDTO {
    private Integer id;
    private CompraDTO compraDTO;
    private String nombreRepartidor;
}