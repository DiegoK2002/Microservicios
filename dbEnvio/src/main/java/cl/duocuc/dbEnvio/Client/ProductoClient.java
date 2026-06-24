package cl.duocuc.dbEnvio.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duocuc.dbEnvio.Dto.ProductoDTO;

@FeignClient(name = "Producto", url = "http://localhost:8083")
public interface ProductoClient {

    @GetMapping("/api/v1/productos/dto/{id}")
    ProductoDTO obtenerDatosProducto(@PathVariable("id") Integer id);
}