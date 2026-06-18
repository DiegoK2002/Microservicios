package cl.duocuc.dbCompra.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duocuc.dbCompra.Model.Compra;
import cl.duocuc.dbCompra.Model.Promociones;
import cl.duocuc.dbCompra.Repository.PromocionesRepository;
import cl.duocuc.dbCompra.Service.CompraService;
import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import cl.duocuc.dbCompra.Client.ProductoClient;
import cl.duocuc.dbCompra.Client.UsuarioClient;
import cl.duocuc.dbCompra.Dto.CompraDTO;
import cl.duocuc.dbCompra.Dto.ProductoDTO;
import cl.duocuc.dbCompra.Dto.UsuarioDTO;

@RestController
@RequestMapping("/api/v1/compras")
@Tag(name = "Compras", description = "Operaciones sobre las compras")
public class CompraController {
    @Autowired
    private CompraService service;

    @Autowired
    private ProductoClient productoClient;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private PromocionesRepository repo;

    @GetMapping
    @Operation(summary = "Retorna la lista completa compras")
    public ResponseEntity<List<Compra>> listarCompras(){
        List<Compra> listaCompras = service.listaCompras();
        if (listaCompras.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(listaCompras);
        }
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Retorna una compra mediante el ID", description = "Retorna el DTO de la compra con los datos enriquecidos de Producto y Usuario")
    public ResponseEntity<CompraDTO> buscarDTO(@PathVariable Integer id) {
        try {
            Compra compra = service.buscarPorId(id);
            if (compra == null) return ResponseEntity.notFound().build();

            ProductoDTO producto = productoClient.obtenerProductoDTO(compra.getIdProducto());
            UsuarioDTO usuario = usuarioClient.obtenerUsuarioDTO(compra.getIdUsuario());
        
        // Buscamos la promoción en la BD
            Promociones promocion = repo.findById(compra.getIdPromocion())
                                    .orElse(new Promociones(null, 0.0, "0%"));

        // 3. Construcción del DTO
            CompraDTO compraDTO = new CompraDTO();
            compraDTO.setId(compra.getId());
            compraDTO.setDiaCompra(compra.getDiaCompra());
            compraDTO.setMesCompra(compra.getMesCompra());
            compraDTO.setAnoCompra(compra.getAnoCompra());
            compraDTO.setProductoDTO(producto);
            compraDTO.setUsuarioDTO(usuario);
        
        // Calcular y asignar el valor total correctamente
            Integer valorTotal = calcularValorTotal(producto, promocion);
            compraDTO.setValorTotal(valorTotal);

            return ResponseEntity.ok(compraDTO);

        } catch (FeignException.NotFound e) { 
        // Si usas Spring Cloud OpenFeign, esto captura específicamente si el producto/usuario no existen
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
        // Cualquier otro error (caída de server, error de código) devuelve un 500 para saber que algo explotó
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Método para que pueda calcular el valor total
        private Integer calcularValorTotal(ProductoDTO producto, Promociones promocion) {
        if (producto == null || producto.getPrecio() == null) return 0;
    
        double precioOriginal = producto.getPrecio();
    // Aplicamos el descuento: precio * (1 - porcentaje)
        double precioFinal = precioOriginal * (1.0 - promocion.getPorcentaje());
    
        return (int) precioFinal;
    }

    @PostMapping
    @Operation(summary = "Registra una nueva compra")
    public ResponseEntity<Compra> guardarCompra(@RequestBody Compra compra) {
        Compra nuevaCompra = service.guardarCompra(compra);
        return ResponseEntity.ok(nuevaCompra);
    }
}