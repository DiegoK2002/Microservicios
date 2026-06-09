package cl.duocuc.dbEnvio.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duocuc.dbEnvio.Client.CompraClient;
import cl.duocuc.dbEnvio.Client.NotificacionClient;
import cl.duocuc.dbEnvio.Dto.CompraDTO;
import cl.duocuc.dbEnvio.Dto.NotificacionDTO;
import cl.duocuc.dbEnvio.Model.Envio;
import cl.duocuc.dbEnvio.Repository.EnvioRepository;

@Service
public class EnvioService {

    @Autowired
    private EnvioRepository repo;

    @Autowired
    private CompraClient compraClient;

    @Autowired
    private NotificacionClient notificacionClient;

    public List<Envio> listaEnvios() {
        return repo.findAll();
    }

    public Envio buscarPorId(Integer id) {
        return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Envio no encontrado"));
    }

    public Envio guardarEnvio(Envio envio) {
    CompraDTO compra = compraClient.obtenerCompraPorId(envio.getIdCompra());
    if (compra == null) {
        throw new RuntimeException("Compra no encontrada");
    }

    Envio envioGuardado = repo.save(envio);

    NotificacionDTO notificacion = new NotificacionDTO(
        "cliente@email.com",
        "Tu envío #" + envioGuardado.getId() + " ha sido creado",
        java.time.LocalDate.now().toString(),
        "Enviada"
    );
    notificacionClient.enviarAlertaEnvio(notificacion);

    return envioGuardado;
}
}