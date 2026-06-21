package cl.friki.Login.dto;

import lombok.Data;

@Data
public class DireccionRegisterDTO {
    private String numVivienda;
    private String calle;
    private String ciudad;
    private String region;
}