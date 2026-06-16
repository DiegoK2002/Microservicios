package cl.friki.Producto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.friki.Producto.dto.ProductoDTO;
import cl.friki.Producto.model.Producto;
import cl.friki.Producto.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "Operaciones sobre productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @GetMapping
    @Operation(summary = "Lista todos los productos")
    public ResponseEntity<List<Producto>> listarProductos(){
        
        List<Producto> listaProductos = service.listarProducts();

        if(listaProductos.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaProductos);
        }
    }

    //buscar producto por id
    @GetMapping("/{id}")
    @Operation(summary = "Retorna un producto mediante el ID", description = "Retorna un producto mediante el ID proporcionado")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Paciente encontrado"),
                            @ApiResponse(responseCode = "404", description = "Paciente no encontrado"),
                            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Producto> buscarPorId(@PathVariable Integer id){
        try{
            Producto producto = service.buscarPorId(id);
            return ResponseEntity.ok(producto);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    //crear producto nuevo
    @PostMapping
    @Operation(summary = "Registra un nuevo producto")
    public ResponseEntity<?> guardar(@RequestBody Producto producto){
        try{
            return ResponseEntity.ok(service.crearProducto(producto));
        } catch (Exception e){
            // Esto te permitirá ver en los logs por qué falló exactamente
        return ResponseEntity.status(400).body("Error al guardar: " + e.getMessage());
        }
    }

    //eliminar producto
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un producto por ID", description = "Elimina del sistema un producto mediante el ID proporcionado")
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        try{
            service.eliminarProducto(id);
            return ResponseEntity.noContent().build();
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    //actualizar producto
    @PutMapping("/{id}")
    @Operation(summary = "Actualiza los datos del producto mediante el ID", description = "Actualiza los datos buscando el ID del producto y reemplazando la información de este por los datos nuevos")
    public ResponseEntity<Producto> actualizar(@PathVariable Integer id, @RequestBody Producto producto){
        try{
            return ResponseEntity.ok(service.actualizarProducto(id, producto));
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    //buscar dto por id
    @GetMapping("/dto/{id}")
    @Operation(summary = "Retorna un producto DTO segun el ID proporcionado", description = "Metodo que permite retonar un producto DTO, normalmente se usa cuando otro microservicio del sistema necesita datos de un producto")
    public ResponseEntity<ProductoDTO> obtenerProductoDTO(@PathVariable Integer id){
        try{
            Producto producto = service.buscarPorId(id);

            ProductoDTO dto = new ProductoDTO();

            dto.setId(producto.getId());
            dto.setNombreProducto(producto.getNombreProducto());
            dto.setPrecio(producto.getPrecio());

            return ResponseEntity.ok(dto);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    @Operation(summary = "Busca un producto mediante su nombre")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        List<Producto> productos = service.buscarPorNombre(nombre);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }
}