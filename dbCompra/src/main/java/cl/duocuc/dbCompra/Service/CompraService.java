package cl.duocuc.dbCompra.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duocuc.dbCompra.Client.ProductoClient;
import cl.duocuc.dbCompra.Client.UsuarioClient;
import cl.duocuc.dbCompra.Dto.ProductoDTO;
import cl.duocuc.dbCompra.Dto.UsuarioDTO;
import cl.duocuc.dbCompra.Model.Compra;
import cl.duocuc.dbCompra.Repository.CompraRepository;

@Service
public class CompraService {
    @Autowired
    private CompraRepository repo;

     @Autowired
    private ProductoClient productoClient;

    @Autowired
    private UsuarioClient usuarioClient;

    public List<Compra> listaCompras(){
        return repo.findAll();
    }

    public Compra buscarPorId(Integer id){
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Compra no encontrado"));
    }

    public Compra guardarCompra(Compra compra) {
        UsuarioDTO usuario = usuarioClient.obtenerDatosUsuario(compra.getIdUsuario());
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        ProductoDTO producto = productoClient.obtenerDatosProducto(compra.getIdProducto());
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado");
        }

        if (compra.getMetodoPago() == null) {
            throw new RuntimeException("Debe seleccionar un método de pago");
        }

        return repo.save(compra);
    }
}
