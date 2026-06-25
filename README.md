Descripción del proyecto:
La sociedad de tiendas "El FrikiFrikon" son un conjunto de tiendas las cuales se dedican a la venta de artículos "geek" la cual en los últimos meses ha tenido 
pocas ventas y no se han actualizado en mucho tiempo, por lo que a nuestro equipo se nos han encargado la tarea de crear un sitio web de ventas al estilo 
"Marketplace" o "Mercado Libre".

Nombres de los integrandes:
- Harol Sarmiento
- Diego Kohle
- Joaquín Dupre

Listado de microservicios:
- Login
- Usuario
- Producto
- Compra
- Reseñas
- Envio
- Reembolsos
- Notificaciones
- Reportes
- Remuneraciones
Rutas principales del Gateway:
id: Login
uri: lb://Login
predicates:
Path=/api/v1/login, /api/v1/register, /api/v1/roles/**

id: Usuario
uri: lb://Usuario
predicates:
Path=/api/v1/usuarios/**

id: Producto
uri: lb://Producto
predicates:
Path=/api/v1/productos/**

id: dbCompra
uri: lb://dbCompra
predicates:
Path=/api/v1/compras/**

id: dbResenas
uri: lb://dbResenas
predicates:
Path=/api/v1/resenas/**

id: dbEnvio
uri: lb://dbEnvio
predicates:
Path=/api/v1/envios/**

id: Reembolsos
uri: lb://Reembolsos
predicates:
Path=/api/v1/reembolsos/**

id: Notificaciones
uri: lb://Notificaciones
predicates:
Path=/api/v1/notificaciones/**

id: dbReportes
uri: lb://dbReportes
predicates:
Path=/api/v1/reportes/**

id: Remuneraciones
uri: lb://Remuneraciones
predicates:
Path=/api/v1/remuneraciones/**

Enlaces de documentación Swagger:
http://localhost:8081/swagger-ui/index.html
http://localhost:8082/swagger-ui/index.html
http://localhost:8083/swagger-ui/index.html
http://localhost:8084/swagger-ui/index.html
http://localhost:8085/swagger-ui/index.html
http://localhost:8086/swagger-ui/index.html
http://localhost:8087/swagger-ui/index.html
http://localhost:8088/swagger-ui/index.html
http://localhost:8089/swagger-ui/index.html
http://localhost:8090/swagger-ui/index.html

Instrucciones basicas de ejecución:
1) Abra en Visual Studio Code o en su editor de confianza los microservicios, el cliente de eureka y el gateway.
2) Abra Xampp y ingrese a la base de datos.
3) Cree la base de datos de los microservicios (excepto el cliente de eureka y el gateway).
4) Primero ponga a correr el cliente de eureka y el gateway y despues el resto de microservicios
5) Abra Postman y siga las rutas de los controllers para poder ejecutar los Endpoints de los microservicios
