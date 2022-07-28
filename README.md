# ML Searching Product

Aplicación que permite hacer búsqueda y detalle de un producto utilizando las API de búsqueda de MercadoLibre disponibles para uso público

## Estructura del Proyecto

La aplicación contiene una estructura donde cada *feature* o funcionalidad es representado a través de un paquete distinto. Entre los paquetes se incluyen:

- **SearchProduct** - La sección gestiona la solicitud y resultado de la búsqueda de un producto ingresado por el usuario. Al hacer la petición de información se podrá ver si ocurren errores conectividad, avisos para productos no encontrados, o se redirige al listado de productos en caso de éxito.
- **FoundProducts** - Sección que muestra un listado con los resultados obtenidos al realizar la búsqueda en *SearchProduct*. De indica al usuario la información básica del producto; su imágen referencial, título, precio, cuotas disponibles para pago y si posee envío gratuito o no.
- **Common** - Paquete utilizado en el caso de que exista funcionalidades compartidas entre secciones.

Dentro de cada paquete se maneja *Clean Architecture*. De ser necesario, se incluye la capa de Presentación (Presentation), Dominio (Domain) y Dato (Data) relacionado a cada funcionalidad en su paquete correspondiente. Con esto tenemos: 
- **Domain** -  Contiene el modelado de entidades que pertenecen al universo relacionado al negocio. En este caso incluye tanto las entidades de Búsqueda (*SearchProduct*) y Producto (*Product*); así como sus *Objetos de Valor* relacionados (*Shipping*, *Installments*,etc.).
- **Data** - Contiene clases que permiten tener acceso a las fuentes de datos. Los modelos de esta capa están directamente relacionados a la fuente y se traducen en modelos de la capa de Dominio mediante el uso de *mapeadores* (Mappers). En este proyecto la fuente de datos se orienta al uso solicitudes REST al API de Mercado Libre.
- **Presentation**- Encapsula todo lo relacionado a la interfaz de usuario de cada vista de la aplicación. Se hace uso de la estructura de *"Flujo de datos Unidireccionales"* donde se definen eventos que genera el usuario y producen cambios de estados que son inmutables, ya que se evita la existencia de estados excluyentes al crearse la copia de un estado previo y luego se modifica el valor deseado con el cambio. En esta capa tambien se utilizan *Mappers* para transformar los modelos del dominio a modelos de *Interfaz de Usuario*

- **Testing** - De forma adicional, se incluyen pruebas de integración desarrolladas en el viewModel (FetchProductsQueryViewModel) considerando el core de la aplicación. Se toma esta clase ya que involucra los manejos de estados que necesitan las vistas para mostrar información, así como el gatillado de acciones para tener acceso a dicha información.

## Librerías utilizadas

- [Navigation Component] : Sirve para enlazar la navegacion entre las diferentes secciones de la aplicación. Para esto se implementó una Actividad base del que se crea un contenedor (*Navigation Host*) que sirve para intercambiar los fragmentos de los que se compone cada vista del app.

- [Retrofit] :  Permite la gestión de las conexiones REST entre la aplicación y los servidores de Mercado Libre. A través de su implementación es posible incluir un cliente HTTP para realizar las peticiones ([OkHttp]), interceptores de las peticiones para el manejo de errores, librerías que permiten parsear respuestas JSON que vienen del servicio ([Moshi]), entre otros.

- [Hilt] : Gestiona la inyección de dependencias entre las diferentes secciones que conforman la aplicación.

- [Coroutines]: Framework utilizado para gestionar subprocesos utilizando mecanismos de suspensión de hilos. Ideal para el manejo de operaciones asincronas.

- [Glide] : Permite la gestión y manejo de carga de imágenes para ser mostrados en las diferentes vistas de la aplicación.

[Navigation Component]: <https://developer.android.com/guide/navigation/navigation-getting-started>
[Retrofit]: <https://square.github.io/retrofit/>
[Hilt]: <https://developer.android.com/training/dependency-injection/hilt-android?hl=es-419>
[Coroutines]: <https://developer.android.com/kotlin/coroutines?gclid=Cj0KCQjw2_OWBhDqARIsAAUNTTGzjPPVu6cGHUpfO16QDrBRXXr81ppbUw0gbpMv5EKsBHOnknFdFSwaAremEALw_wcB&gclsrc=aw.ds>
[Glide]: <https://github.com/bumptech/glide>
[OkHttp]: <https://square.github.io/okhttp/>
[Moshi]: <https://github.com/square/moshi>
