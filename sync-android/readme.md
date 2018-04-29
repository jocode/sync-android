# Sincronización de datos en Android

Este ejemplo está basado en el tutorial de [¿Cómo Sincronizar Sqlite Con Mysql En Android?](http://www.hermosaprogramacion.com/2015/07/como-sincronizar-sqlite-con-mysql-en-android/).

La sincronización de datos en Android es uno de los temas más exigentes en el desarrollo de Aplicaciones, para que éstas conserven los datos de forma exitosa.

La idea clave para sincronizar es **evitar** realizar las **acciones** de actualización dentro del **hilo principal**.

Recuerda que si ejecutas acciones de larga duración en el hilo UI, puede ser nefasto para la experiencia de usuario, causando bloqueos frecuentes en tu app y provocando la furia de muchos.

Para esto se usan algo llamado **Sync Adapters**. Elementos destinados a correr de forma asíncrona las actualizaciones de nuestros datos, apoyándose del framework de sincronización de Android.

Este tema exige conocimientos en:
* Bases de Datos SQLite en aplicaciones Android
* Content Provider
* Servicios: Trabajos en segundo plano
* Realizar Peticiones Http Con La Librería Volley En Android
* Parsear datos JSON en Android con JsonReader y Gson
* Web Service 

## Sincronización de datos en Android

Como bien sabes, la sincronización de datos es el proceso de *copiar automáticamente* un conjunto de *datos entre* uno o más *dispositivos*, de tal forma que la información se encuentre al día.

Hay dos tipos de sincronización:
* __Sincronización Local:__ Los datos que existen en un servidor sean depositados en nuestro dispositivo móvil.
* __Sincronización remota:__ La aplicación realice cambios en el contenido local y sea necesario reflejar este comportamiento en el servidor.

Antes de implementar una solución para este problema, es conveniente que defina las condiciones de sincronización de datos.

Algunas de las cosas que debes pensar antes de desarrollar tu aplicación son:
- ¿El usuario podrá sincronizar los datos manualmente?
- En caso de que la sincronización sea manual, ¿cuál es el tiempo necesario entre una sincronización y otra?
- ¿Cómo evitar que un registro que está pendiente por sincronizarse sea puesto de nuevo en cola?
- ¿Si el dispositivo no tiene conexión de red, de qué forma se controlará la sincronización?
- ¿Cómo evitar que los tipos de datos entre tecnologías afecte la información de las bases de datos?
- ¿Qué estilo de servicio web se implementará (REST, SOAP, etc.)?
- ¿Cuál es el pronóstico del volumen de datos que será sincronizado?
- ¿La sincronización debe ser en tiempo real?
- ¿Qué tan necesario crees que la sincronización debe ser automática y llevada a cabo a una hora determinada del día?
- ¿Cuál será el límite de registros que tendrás en tu base de datos local para evitar sobrecarga?
- ¿Es necesario usar paginado?
- ¿De qué forma entregarás el control de la sincronización en tu actividad de preferencias?


## Arquitectura Basada En Un SyncAdapter

Virgil Dobjanschi en su conferencia “Google I/O 2010 – Android REST client applications” expone tres estilos arquitecturales que podemos usar a la hora de comunicar nuestras aplicaciones Android con un servicio REST.

Para ver la explicación de la Arquitectura usada para la sincronización en el sistema operativo Android, ver el vídep https://youtu.be/xHXn3Kg2IQE

El patrón a utilizar en este ejemplo, está basado en un componente llamado SyncAdapter, cuya funcionalidad es sincronizar datos en segundo plano entre una aplicación Android y un servidor remoto.

![Arquitectura centrada en syncadapter](http://www.hermosaprogramacion.com/wp-content/uploads/2015/07/arquitectura-centrada-en-syncadapter.png)

Se caracteriza por realizar las acciones asíncronicamente, es decir, en periodos de tiempo sin inicio o fin determinados. Por ello puede que la transferencia de datos no suceda cuando esperamos, sin embargo asegurando la integridad de la información.

Esta arquitectura se enfoca en evitar realizar la sincronización en el UI Thread. Lo que libera a nuestras actividades de operaciones en la base de datos, peticiones Http, etc.

Además el content provider permite la actualización de la interfaz de usuario en tiempo real gracias a los Loaders.

Las peticiones Http son manejadas por el mismo SyncAdapter, evitando comprometer el hilo principal que procesa los eventos de interfaz.

## Autenticación De Usuarios En Android
La *sincronización en Android* está ligada al *uso* de *cuentas de usuario* para determinar qué datos asociados serán procesados.

Esto quiere decir que NO es posible usar sincronización sin crear al menos una cuenta asociada a la aplicación.

![Opciones de cuentas en Android](http://www.hermosaprogramacion.com/wp-content/uploads/2015/07/opciones-de-cuentas-en-android-5.png)

Incluso si tu aplicación no usa un login, debe incluirse obligatoriamente la creación de una cuenta local auxiliar que satisfaga este requerimiento.

Por ello, para implementar el esquema de transferencia de datos en Android se deben implementar dos partes: *Autenticación y Sincronización*.

**Autenticación del cliente con el servidor remoto** — Para usar el sistema de Autenticación de Cuentas de Android debes incluir en tu app las siguientes piezas:

1. Una autenticador de cuentas que extienda de la clase `AbstractAccountAuthenticator`. Este elemento se encarga de comprobar las credenciales, escribir las opciones de acceso, crear las cuentas, etc.
2. Un `bound service` que sea lanzado por un intent con el filtro `android.accounts.AccountAuthenticator` y retorne una instancia del autenticador en su método `onBind()`.
3. Una descripción XML que especifique el diseño de la cuenta dentro de la sección `Accounts` de los ajustes de android.

Si tu aplicación no requiere autenticación, entonces debes crear las anteriores piezas sin ninguna funcionalidad, como un soporte auxiliar.


## Implementar Un SyncAdapter

Este proceso es controlado por un elemento del sistema llamado SyncManager, el cual se encarga de añadir a una cola de gestión las sincronizaciones de todas las aplicaciones.

Incluso comprueba que la conexión a la red esté disponible antes de iniciar una sincronización. También se encarga de reiniciar una sincronización fallida por si nuestro Sync Adapter ha fallado.

Para [acceder a este framework](https://developer.android.com/training/sync-adapters/creating-sync-adapter?hl=es) simplemente debemos usar cuatro piezas de código:

1. Un Content provider que proporcione flexibilidad y seguridad a los datos locales. Este elemento es obligatorio. Si tu aplicación no usa un content provider, entonces crea uno auxiliar para satisfacer la característica.
2. Un Sync Adapter que extienda de la clase AbstractThreadedSyncAdapter que maneje la sincronización de la aplicación. Donde se sobrescribe el método onPerfomSync() para indicar las acciones de actualización, peticiones http, parsing, etc.
3. Un bound service que esté registrado para escuchar el filtro android.content.SyncAdapter y que retorne en su método onBind() el Sync Adapter. Este servicio se comunicará con el sistema para controlar la sincronización.
4. Una definición XML que le diga al Sync Manager de qué forma se manipularán los datos a sincronizar.

> La autenticación y la sincronización usan servicios. Esto es para evitar la ruptura de la sincronización en caso de que la aplicación pase a segundo plano o se extienda prolongadamente.

## ¿Cómo se inicia un Sync Adapter?

- **Por Cambios en el servidor—** En este escenario, el Sync Adapter se inicia debido a que se produce una petición desde el servidor hacia el dispositivo Android, cuando los datos en él cambian . A esto se le llama notificaciones push y podemos implementarlo con el servicio de Firebase Cloud Messaging.
- **Por Cambios en el contenido local—** Cuando los datos del Content Provider son modificados en la aplicación local, el Sync Adapter puede iniciarse automáticamente para subir los datos nuevos al servidor y asegurar una actualización.
- **Al enviar mensaje de red—** Android comprueba la disponibilidad de la red enviando un mensaje de prueba con frecuencia. Podemos indicar a nuestro Sync Adapter que se inicie cada vez que este mensaje es liberado.
- **Programando intervalos de tiempo—** En este caso podemos programar el Sync Adapter para que se ejecute cada cierto tiempo continuamente o si lo deseas, en una hora determinada del día.
- **Manualmente (por demanda)—** El sincronizador se inicia por petición del usuario en la interfaz. Por ejemplo, pulsando un action button.

![Sincronización manual](http://www.hermosaprogramacion.com/wp-content/uploads/2015/07/sincronizaci%C3%B3n-manual-palabred.png)


## Explicación de la Aplicación Android Con Sincronización - Ejemplo

La aplicación de ejemplo que crearemos se llama *“Crunch Expenses”*. Su propósito es guardar los gastos personales que el usuario tiene a lo largo de su jornada.

![Ejemplo Sincronización](http://www.hermosaprogramacion.com/wp-content/uploads/2015/07/lista-con-recycler-view-en-android.png)

La información está soportada en un `servicio web` implementado con Mysql y Php en el servidor local. La idea es `cargar` la lista completa de registros en la aplicación a través de un `Sync Adapter`.

Adicionalmente se permitirá añadir gastos a la base de datos local que a su vez deben ser programados inmediatamente para ser sincronizados remotamente.

Sólo de usará **sincronización por demanda** para ver un efecto inmediato de las acciones que vamos a realizar.

Las tareas a realizar son:

- [ ] **Crear servicio web con Php y Mysql**
1. Diseñar e implementar base de datos
2. Implementar conexión a la base de datos
3. Implementar métodos de operación de datos

- [ ] **Desarrollar Aplicación Android**
4. Creación De Content Provider
5. Preparar capa de conexión con Volley
6. Añadir autenticación de usuarios
7. Añadir SyncAdapter
8. Implementar adaptador del RecyclerView
9. Creación de la actividad principal
10. Creación de la actividad de inserción

La explicación de cada una de las tareas se realizarán en archivos separados para evitar la sobrecarga de información en esta página, y de alguna manera modularizar el ejemplo para poder haciendolo parte por parte, porque es bastante largo.







