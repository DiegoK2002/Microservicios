package cl.duocuc.dbReportes.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportesDTO {
    private Integer id;
    private Integer ventasXMes;
    private Integer ventasTotales;
}
