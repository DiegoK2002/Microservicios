package cl.duocuc.dbEnvio.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cl.duocuc.dbEnvio.Dto.NotificacionDTO;

@FeignClient(name = "Notificaciones", url = "http://localhost:8088")
public interface NotificacionClient {

    @PostMapping("/api/v1/notificaciones")
    NotificacionDTO enviarAlertaEnvio(@RequestBody NotificacionDTO notificacion);
}