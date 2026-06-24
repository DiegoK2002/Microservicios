package cl.duoc.Remuneraciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemuneracionDTO {

    private Integer id;
    private String nombreEmpleado;
    private Double salarioBase;
    private Double bonificacion;
    private Double descuentos;
    private Double totalPagado;
    private String fechaPago;
    private String estado;
    private String descripcion;
}