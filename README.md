## Proyecto de Desarrollo de API para Agencia de Turismo

### Introducción

Este proyecto tiene como objetivo desarrollar una API REST para una agencia de turismo que ofrece servicios de búsqueda y reserva de hoteles y vuelos. La implementación se basa en los conocimientos adquiridos durante un BOOTCAMP, abarcando tecnologías como Git, Java, Spring Boot, Testing, JPA + Hibernate y Spring Security.

### Funcionalidades

El sistema proporciona las siguientes funcionalidades:

#### Hoteles

- **Obtener un listado de todos los hoteles registrados**
  - *Método:* GET
  - *Path:* /agency/hotels

- **Obtener un listado de todos los hoteles disponibles en un rango de fechas y según el destino seleccionado.**
  - *Método:* GET
  - *Path:* /agency/hotels/search?dateFrom=dd/mm/aaaa&dateTo=dd/mm/aaaa&destination="nombre_destino"

- **Realizar una reserva de un hotel**
  - *Método:* POST
  - *Path:* /agency/hotel-booking/new

#### Vuelos

- **Obtener un listado de todos los vuelos registrados**
  - *Método:* GET
  - *Path:* /agency/flights

- **Obtener un listado de todos los vuelos disponibles en un rango de fechas y según el destino y el origen seleccionados.**
  - *Método:* GET
  - *Path:* /agency/flights/search?dateFrom=dd/mm/aaaa&dateTo=dd/mm/aaaa&origin="ciudadOrigen"&destination="ciudadDestino"

- **Realizar una reserva de un vuelo.**
  - *Método:* POST
  - *Path:* /agency/flight-booking/new

#### Operaciones de Base de Datos

Se permite la realización de operaciones de alta, baja y modificación sobre una base de datos tanto para la gestión de vuelos como para la gestión de hoteles.

- *Métodos:* GET, POST, PUT y DELETE
- *Paths Hoteles:*
  - /agency/hotels/new ---------> Permite dar de alta hoteles en la base de datos. (POST)
  - /agency/hotels/edit/{id} ---> Permite editar un hotel existente a partir de su ID. (PUT)
  - /agency/hotels/delete/{id} -> Permite eliminar un hotel existente a partir de su ID. (DELETE)
  - /agency/hotels/{id} --------> Permite la busqueda de un hotel por su ID. (GET)
  - /agency/hotels -------------> Permite la busqueda de todos los hoteles. (GET)
- *Paths Vuelos:*
  - /agency/flights/new --------> Permite dar de alta vuelos en la base de datos. (POST)
  - /agency/flights/edit/{id} --> Permite editar un vuelo existente a partir de su ID. (PUT)
  - /agency/flights/delete/{id}-> Permite eliminar un vuelo existente a partir de su ID. (DELETE)
  - /agency/flights/{id} -------> Permite la busqueda de un vuelo por su ID. (GET)
  - /agency/flights ------------> Permite la busqueda de todos los vuelos. (GET)
- *Paths Reserva Hoteles:*
  - /agency/hotel-booking/new ---------> Permite realizar la reserva de un hotel. (POST)
  - /agency/hotel-booking -------------> Permite la busqueda de todos las reservas. (GET)
  - /agency/hotel-booking/{id} --------> Permite la busqueda de una reserva por su ID. (GET)
  - /agency/hotel-booking/delete/{id} -> Permite eliminar una reserva existente a partir de su ID. (DELETE)
  - /agency/hotel-booking/edit/{id} -> Permite editar una reserva existente a partir de su ID. (PUT)
- *Paths Reserva Vuelos:*
  - /agency/flight-booking/new ---------> Permite realizar la reserva de un vuelo. (POST)
  - /agency/flight-booking -------------> Permite la busqueda de todos las reservas. (GET)
  - /agency/flight-booking/{id} --------> Permite la busqueda de una reserva por su ID. (GET)
  - /agency/flight-booking/delete/{id} -> Permite eliminar una reserva existente a partir de su ID. (DELETE)
  - /agency/flight-booking/edit/{id} -> Permite editar una reserva existente a partir de su ID. (PUT)

-*Clase User:* El alta de los usuarios en la base de datos se da cuando realizan la reserva y se piden sus datos.
 
  Todos estos endpoints vienen más ampliamente especificados y desarrollados en la documentación de Swagger. Puedes acceder a ella una vez ejecutas el proyecto y a través de la url:
  http://localhost:8080/doc/swagger-ui.html
  
### Seguridad
Se trata de una aplicación con seguridad a través de SpringSecurity. Sin embargo, algunos de los endpoints deben ser accesibles por los clientes para poder visualizar los hoteles o vuelos y poder hacer una reserva.
Los siguientes endpoints no necesitan de autenticación:
  - /agency/hotels/search 
  - /agency/hotels 
  - /agency/flights
  - /agency/flights/search 
  - /agency/flight-booking/new 
  - /agency/hotel-booking/new
    
### Test unitario
Se realiza un test unitario con JUnit 5 para chequear que la creación de un vuelo en la capa service funcione correctamente.

### Archivos Adjuntos

En la carpeta `resources` del proyecto encontrarás los siguientes archivos importantes:

- **Base de Datos:** Se adjunta un archivo SQL (`agencia_turismo.sql`) que contiene la estructura y datos iniciales de la base de datos utilizada por la aplicación.

- **JSON de Ejemplo para Hoteles:** Encuentra un archivo JSON (`JsonHoteles.json`) con datos de ejemplo para la gestión de hoteles.

- **JSON de Ejemplo para Vuelos:** Se proporciona un archivo JSON (`JsonVuelos.json`) con datos de ejemplo para la gestión de vuelos.


### Supuestos o Consideraciones

1. No se creó una clase Room o Habitación dado que la agencia saca ofertas esporádicas de cada hotel, la implementación de esta clase podría ser añadida en un futuro.

2. La agencia solo ofrece hoteles que ofrecen tipos de habitación: "Single," "Double," o "Triple."

3. Hoteles y vuelos ofrecen una única reserva dentro de las fechas disponibles. Si esta se cubre, se debe crear otra oferta de hotel o vuelo.

4. Por facilitar el acceso a la corrección, la contraseña se deja en el application.properties. En un proyecto real deberia estar en una variable de entorno o mediante valores encriptados.

### Consideraciones Adicionales

Se han realizado validaciones que podrían ser más óptimas validarlas en el frontend, como definir el tipo de asiento o tipo de habitación, o bloquear fechas pasadas en el calendario. Estas validaciones se han implementado en el backend por motivos de consistencia en los datos. 