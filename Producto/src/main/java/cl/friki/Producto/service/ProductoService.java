package cl.friki.Producto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.friki.Producto.model.Producto;
import cl.friki.Producto.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    //buscar a todos los productos
    public List<Producto> listarProducts(){
        return productoRepository.findAll();
    }

    //buscar producto por id
    public Producto buscarPorId(Integer id){
        return productoRepository.findById(id).orElseThrow(() -> new RuntimeException("no se encontró ese producto"));
    }
    
    //crear producto
    public Producto crearProducto(Producto producto){
        return productoRepository.save(producto);
    }

    //actualizar un producto
    public Producto actualizarProducto(Integer id, Producto productoActualizado){
        Producto productoAnt = productoRepository.findById(id).orElseThrow(()-> new RuntimeException("Producto no encontrado"));
        
        productoAnt.setNombreProducto(productoActualizado.getNombreProducto());
        productoAnt.setCantProducto(productoActualizado.getCantProducto());
        productoAnt.setPrecio(productoActualizado.getPrecio());
        productoAnt.setTipoProducto(productoActualizado.getTipoProducto());

        return productoRepository.save(productoAnt);
    }

    //eliminar un producto
    public void eliminarProducto(Integer id){
        if(productoRepository.existsById(id)){
            productoRepository.deleteById(id);
            
        }else{
           throw new RuntimeException("Producto no encontrado"); 
        }
    }



}
