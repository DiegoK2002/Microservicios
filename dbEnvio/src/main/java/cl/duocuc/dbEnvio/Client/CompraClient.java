package cl.duocuc.dbEnvio.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duocuc.dbEnvio.Dto.CompraDTO;

@FeignClient(name = "dbCompra", url = "http://localhost:8084")
public interface CompraClient {
    @GetMapping("/api/v1/compras/dto/{id}")
    CompraDTO obtenerCompraPorId(@PathVariable("id") Integer id);
}