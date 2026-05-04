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
import org.springframework.web.bind.annotation.RestController;

import cl.friki.Producto.dto.ProductoDTO;
import cl.friki.Producto.model.Producto;
import cl.friki.Producto.service.ProductoService;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @GetMapping
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
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto){
        return ResponseEntity.ok(service.crearProducto(producto));
    }

    //eliminar producto
    @DeleteMapping("/{id}")
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
    public ResponseEntity<Producto> actualizar(@PathVariable Integer id, @RequestBody Producto producto){
        try{
            return ResponseEntity.ok(service.actualizarProducto(id, producto));
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    //buscar dto por id
    @GetMapping("/dto/{id}")
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
}
