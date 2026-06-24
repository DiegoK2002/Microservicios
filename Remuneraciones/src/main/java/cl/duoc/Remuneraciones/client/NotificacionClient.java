package cl.duoc.Remuneraciones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cl.duoc.Remuneraciones.dto.NotificacionRequestDTO;

/**
 * Cliente Feign hacia el microservicio Notificaciones.
 * El name corresponde al spring.application.name registrado en Eureka
 * (notificaciones-service), por eso se resuelve vía Eureka sin url fija.
 */
@FeignClient(name = "notificaciones-service")
public interface NotificacionClient {

    @PostMapping("/api/v1/notificaciones")
    void enviarNotificacion(@RequestBody NotificacionRequestDTO request);
}
