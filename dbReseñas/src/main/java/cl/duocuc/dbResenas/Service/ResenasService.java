package cl.duocuc.dbResenas.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duocuc.dbResenas.Client.ProductoClient;
import cl.duocuc.dbResenas.Dto.ProductoDTO;
import cl.duocuc.dbResenas.Model.Resenas;
import cl.duocuc.dbResenas.Repository.ResenasRepository;

@Service
public class ResenasService {
    @Autowired
    private ResenasRepository repo;

    @Autowired
    private ProductoClient productoClient;

    public List<Resenas> listaReseñas(){
        return repo.findAll();
    }

    public Resenas buscarPorId(Integer id){
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Reseña no encontrada"));
    }

    public Resenas guardarResenas(Resenas resenas) {
    ProductoDTO producto = productoClient.verificarProductoExistente(resenas.getIdProducto());
    if (producto == null) {
        throw new RuntimeException("Producto no encontrado");
    }
 
    resenas.setNombreProducto(producto.getNombreProducto()); 

    return repo.save(resenas);
}

    public void eliminarResena(Integer id) {
        if(repo.existsById(id)){
            repo.deleteById(id);
        } else {
            throw new RuntimeException("Reseña no encontrado");
        }
    }
}
