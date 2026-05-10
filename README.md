# p2-proyecto-final
Repositorio para el proyecto final de Programación 2, enfocado en el uso de patrones de diseño.
Pensamiento computacional:

— ¿Qué se solicita finalmente?
Se solicita desarrollar una plataforma integral que permita a los usuarios finales explorar y comprar entradas para eventos (conciertos, teatro, conferencias), y a los administradores gestionar toda la logística y métricas del negocio. 

— ¿Qué información es relevante?
Entidades de Negocio: Usuarios, eventos, recintos, zonas, asientos, compras, entradas e incidencias.
Estados: De compras (Pagada, Reembolsada,cancelada, creada, confirmada, incidencia).
Reglas de Negocio: Políticas de cancelación, precios base por zona y servicios adicionales.

— ¿Cómo se agrupa la información relevante? (clases y estructura)

• Usuario — idUsuario, nombre, correo, teléfono, métodos de pago
• Evento — idEvento, nombre, categoría, ciudad, fecha, estado, políticas, recinto
• Recinto — idRecinto, nombre, dirección, ciudad, zonas
• Zona — idZona, nombre, capacidad, precio base, asientos.
• Asiento — idAsiento, fila, número, estado (Disponible/Reservado/Vendido/Bloqueado)
• Compra — idCompra, usuario, evento, total, fecha, estado, entradas, servicios adicionales. + builder. CompraFacade proporciona una fachada para el cliente
• Incidencia — idIncidencia, tipoInicdencia, descripcion, fecha. + interface IIncidencia
• Entrada — EntradaDecorator + decorator concretos. EntradaFactory (para crear entrada preferencia, general o VIP)
• GeneradorSesion — usuarios, usuarioActual. Singleton 
• NotificacionObserver <<interface>> — la implementan EmailObserver y SMSObserver.
• IEstadoCompra <<interface>>— states concretos (creada, pagada, cancelada, confirmada, reembolsada, incidencia).

— ¿Qué funcionalidades se solicitan?

•Para el Usuario:Registro/login, explorar y filtrar eventos, seleccionar zona/asiento, comprar entradas, gestionar compras (crear/modificar/cancelar), agregar servicios VIP, historial de compras, descargar reportes CSV/PDF. 

•Para el Administrador: CRUD usuarios, eventos, recintos, zonas, asientos, control de disponibilidadGestión de compras, registro de incidencias, panel de métricas con JavaFX, chartsReportes operativos (ventas, ocupación, top eventos, tasa de cancelación) 

— ¿Cómo se distribuyen las funcionalidades?

•Funcionalidades para el Usuario:

-Registro / Login: Esta responsabilidad recae en GestorSesion (que maneja la lista de usuarios y el usuarioActual) apoyándose en la clase Usuario.

-Explorar y filtrar eventos: Se gestiona a través de la clase Evento, que contiene los atributos de nombre, categoria y ciudad para aplicar los filtros.

-Seleccionar zona / asiento: Se realiza mediante la navegación de Recinto ->Zona  -> Asiento. La lógica de selección se vincula a estas entidades.

-Comprar entradas: La clase principal es Compra, pero la interacción simplificada se realiza a través de CompraFacade (método iniciarCompra).

-Gestionar compras (Crear / Modificar / Cancelar): Se delega en CompraFacade (métodos confirmarPago y cancelarCompra) y el estado interno cambia mediante las implementaciones de IEstadoCompra.

-Agregar servicios VIP: Se utiliza el patrón Decorator. La funcionalidad reside en EntradaDecorator y sus hijos: SeguroDec, ParqueaderoDec y MerchandasingDec.
-Historial de compras: Aunque no hay una clase "Historial", la lógica de recuperación de datos perteneceríade compra hacia usuario.

-Descargar reportes CSV / PDF: Se gestiona mediante la interfaz IGeneradorReporte y sus adaptadores POIAdapter (Excel/CSV) y PDFBoxAdapter (PDF).
•Funcionalidades para el Administrador
-CRUD usuarios: Se especifica directamente en la clase GestorSesion con el método +CRUD usuarios....

-CRUD eventos, recintos, zonas y asientos: Estas operaciones de administración suelen gestionarse en clases controladoras o servicios vinculados directamente a las entidades Evento, Recinto, Zona y Asiento.

-Control de disponibilidad: Se verifica en la clase Evento (el estadoEvento no puede ser “cancelado” ), Asiento (atributo estado: EstadoAsiento) y en Zona (atributo capacidad).

-Gestión de compras (Administrativa): Se centraliza en CompraFacade para supervisar los flujos de todas las transacciones.

-Registro de incidencias: Se asigna a la clase Incidencia y su interfaz IIncidencia, que permite reportar problemas sobre entidades afectadas.

-Panel de métricas (JavaFX / Charts): El diagrama no muestra clases de UI, pero la lógica de obtención de datos para estas métricas provendría de IGeneradorReporte procesando la información de Compra y Evento.

-Reportes operativos (Ventas, ocupación, etc.): Al igual que los reportes de usuario, se resuelven mediante el patrón Adapter con IGeneradorReporte, POIService y PDFBoxService, extrayendo la data de las colecciones de compras y zonas.

— ¿Qué debo hacer para probar las funcionalidades?

Inicializar datos de prueba que cubran todos los escenarios:
• Al menos 2 usuarios (1 admin, 1 usuario normal) con métodos de pago

• Al menos 2 eventos en distintos estados (Publicado, Pausado, Cancelado)

• 1 recinto con zonas VIP/Preferencial/General y asientos numerados

• Compras en cada estado (Creada, Pagada, Confirmada, Cancelada, Reembolsada)

• Incidencias registradas para validar el panel admin 

— ¿Qué puedo reutilizar? (patrones elegidos)

• Patrón Singleton : 
Requisito que resuelve: Registro/Login y Gestión de Sesión (RF: Gestión de acceso).RF-001   RF-002   RF-020   RF-012 
Problema que resuelve: Evita que existan múltiples instancias de la sesión del usuario, lo que causaría conflictos sobre quién es el "usuario actual" y duplicidad en la lista de usuarios en memoria.
Por qué este y no otro: A diferencia de una clase estática simple, el Singleton permite implementar interfaces y facilita el control de acceso global a los datos del sistema (usuarios, eventos cargados) desde cualquier punto de la aplicación sin pasar el objeto por todos los constructores.

• Factory Method: 
Requisito que resuelve: Generar diferentes tipos de entrada.RF-005  RF-038  RF-029 
Problema que resuelve: El sistema no sabe de antemano qué tipo de entrada elegirá el usuario. Centraliza la lógica de creación para evitar llenar el código de bloques switch o if cada vez que se necesita una entrada.
Por qué este y no otro: Se prefiere sobre la instanciación directa (new EntradaVip()) para desacoplar al cliente de las clases concretas. Si mañana se crea una "Entrada Platino", solo se modifica la fábrica (EntradaFactory) y no el resto del sistema.  Se descartó Builder porque una Entrada individual no tiene pasos opcionales — solo necesita tipo y zona.  

• Builder: 
Requisito que resuelve: Crear y modificar una compra antes de confirmar el pago, incluyendo la adición progresiva de entradas y servicios adicionales. RF-006  RF-034  RF-035  RF-009 
Problema que resuelve: La creación de un objeto Compra es compleja; requiere asociar un usuario, un evento, múltiples entradas, calcular un total y asignar un estado inicial, lo que exige un objeto parcialmente construido que sea válido en cada paso. 
Por qué este y no otro: Se eligió sobre un "Constructor Telescópico" (muchos parámetros en el constructor) porque el Builder permite construir la compra paso a paso (primero el evento, luego los asientos, luego los adicionales) y solo instanciar el objeto final cuando el usuario confirma.
 
• Decorator: 
Requisito que resuelve: Agregar servicios adicionales (VIP, seguro de cancelación, parqueadero, merchandising) a una entrada y calcular el precio final acumulado. RF-009  RF-007  RF-038 
Problema que resuelve: Permite añadir costos y descripciones adicionales a una entrada base sin crear infinitas subclases (ej. no necesitas una clase EntradaGeneralConSeguroYParqueadero).
Por qué este y no otro: A diferencia de la herencia simple, el Decorador permite "envolver" la entrada dinámicamente en tiempo de ejecución, permitiendo que el usuario combine los servicios que quiera de forma flexible.

• Facade: 
Requisito que resuelve: Todo el flujo de compra: reservar asiento → construir compra → agregar servicios → pagar → notificar → actualizar estado. RF-006  RF-007  RF-008  RF-009 
Problema que resuelve: El subsistema de compras es complejo (interactúa con estados, métodos de pago, decoradores y observadores). La Fachada evita que la interfaz de usuario tenga que conocer toda esa complejidad.
Por qué este y no otro: Se eligió para proporcionar un "punto de entrada único" (CompraFacade). Sin él, el programador de la interfaz tendría que llamar manualmente a 5 o 6 clases distintas para completar una sola compra.

•Adapter:
Requisito que resuelve: Exportar reportes de compras en CSV/PDF para el usuario y reportes operativos (ventas, ocupación, top eventos) para el administrador. RF-011  RF-046  
Problema que resuelve: Las librerías de terceros tienen métodos e interfaces que no coinciden con nuestra estructura. El adaptador "traduce" nuestro método generarReporte() a los métodos específicos de cada librería.
Por qué este y no otro: Permite cumplir con el principio de Inversión de Dependencias. Si se decide cambiar la librería de PDF por otra mejor, solo se crea un nuevo adaptador sin tocar el resto del código del sistema.  Se descartó Strategy porque Strategy es para algoritmos del mismo dominio, aquí son bibliotecas externas con interfaces radicalmente distintas. 

• Strategy: 
Requisito que resuelve: Pagar una compra con distintos métodos simulados y gestionar métodos de pago asociados al perfil del usuario.  RF-007  RF-021 
Problema que resuelve: Cada método de pago tiene una lógica de validación y procesamiento distinta. Este patrón encapsula cada algoritmo en su propia clase.
Por qué este y no otro: Permite cambiar el método de pago en tiempo de ejecución. El usuario puede elegir pagar con Tarjeta o PSE y el objeto Compra simplemente llama a pagar(), sin importarle la implementación interna. Se descartó Template Method porque los algoritmos de pago no comparten pasos estructurales comunes — son completamente distintos.  

• Observer: 
Requisito que resuelve: Notificar cambios de estado de compras y eventos. Registrar incidencias automáticamente ante eventos no comunes. RF-008  RF-017  RF-013  RF-041 
Problema que resuelve: Evita que el sistema tenga que preguntar constantemente si una compra cambió de estado. En su lugar, el objeto de interés (Sujeto) avisa automáticamente a los interesados (Observadores).
Por qué este y no otro: Permite un acoplamiento mínimo. Se puede añadir nuevos canales de notificación (como WhatsApp o Push) simplemente creando un nuevo suscriptor sin modificar la lógica de la compra.

• State:
Requisito que resuelve: Gestionar compras y Control de disponibilidad. RF-008 RF-006 RF-036 RF-016 RF-024 
Problema que resuelve: El comportamiento de los métodos cambia según el estado actual. Por ejemplo, no se puede "cancelar" una compra que ya está en estado "Reembolsada".
Por qué este y no otro: Elimina el uso excesivo de condicionales anidados. Cada estado (PagadaState, CanceladaState) conoce sus propias reglas, haciendo que el código sea mucho más fácil de mantener y expandir.  Se descartó Strategy porque Strategy cambia el algoritmo, no las operaciones permitidas según contexto.  
