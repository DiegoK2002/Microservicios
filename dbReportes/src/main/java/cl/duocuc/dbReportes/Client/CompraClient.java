package cl.duocuc.dbReportes.Client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duocuc.dbReportes.Dto.CompraDTO;

@FeignClient(name = "dbCompra", url = "http://localhost:8084")
public interface CompraClient {
    @GetMapping("/api/v1/compras/dto/{id}")
    CompraDTO obtenerCompraPorId(@PathVariable("id") Integer id);

    @GetMapping("/api/v1/compras/mes/{mes}")
    List<CompraDTO> obtenerCompraPorMes(@PathVariable("mes") Integer mes);

    @GetMapping("/api/v1/compras/año/{ano}")
    List<CompraDTO> obtenerCompraPorAño(@PathVariable("ano") Integer ano);
}