# Servicios en Android

Un servicio es un elemento que lleva a cabo operaciones de larga duración en segundo plano y sin ninguna clase de interfaz.

Se puede utilizar para:
* Sincronizar aplicaciones con la nube.
* Administrar Notificaciones Push (Firebase Cloud Messaging, Parse, etc.).
* Monitorear información.
* Reproducir música sin tener contacto directo con la interfaz.
* Almacenar información en la base de datos.
* Gestionar escritura y lectura de archivos.
* y muchas más…

La ventaja es que se ejecutan en segundo plano, cuando el usuario no tiene contacto con la aplicación, a diferencia de las tareas asíncronas, las cuales se ejecutan si están activas.

Un **servicio** en Android se representa con la clase `Service`

Para iniciar un servicio es similar a iniciar una actividad, usando la clase `Intent` con un intent explícito, y se utiliza en método `startService()`
```java 
startService(new Intent(this, Servicio.class));
```

Del mismo modo, puedes terminar su ejecución con el método `stopService()`

```java
stopService(new Intent(this, Servicio.class));
```

## Tipos de Servicios

Existen dos clases de servicios:
1. **Started Service** Que se inicia explícitamente con el método `startService()`
2. **Bound Service** Que se liga a un componente `bindService()`


## 1. Crear un Started Service

Para crear un servicio en Android, hay que tener en cuenta su [ciclo de vida](https://developer.android.com/guide/components/services.html?hl=es#Lifecycle)

![Ciclo de vida del Servicio en Android](http://www.hermosaprogramacion.com/wp-content/uploads/2015/07/ciclo-de-vida-de-un-servicio-android.png)


Los métodos que controlan su flujo (Ciclo de Vida) son:

* __onCreate()__: Se ejecuta cuando el servicio está creado en memoria. Si el servicio ya está activo, entonces se evita de nuevo su llamada.
* __onStartCommand()__: Método que ejecuta las instrucciones del servicio. Se llama solo si el servicio se inició con startService().
* __onBind()__: Solo se ejecuta si el servicio fue ligado con bindService() por un componente. Retorna una interfaz Java de comunicación del tipo IBinder. Este método siempre debe llamarse, incluso dentro de los started services, los cuales retornan null.
* __onDestroy()__: Se llama cuando el servicio está siendo destruido. Importantísimo que dentro de este método detengas los hilos iniciados.


[El siguiente es un ejemplo de un started service …](examples/started_service.java)

El método `onStartCommand()` retorna en una constante llamada `START_NOT_STICKY`. Este campo hace parte de la clase `Service`, el cual indica que el servicio no debe recrearse al ser destruido sin importar que haya quedado un trabajo pendiente.

De igual modo, también puedes retornar en:

- **START_STICKY**: Crea de nuevo el servicio después de haber sido destruido por el sistema. En este caso llamará a `onStartCommand()` referenciando un intent nulo.
- **START_REDELIVER_INTENT**: Crea de nuevo el servicio si el sistema lo destruyó. A diferencia de `START_STICKY`, esta vez sí se retoma el último intent que recibió el servicio.


Los servicios no se instanciarán si no los declaras en el _Android Manifest_ como un **componente** de la aplicación. Así que solo incluye la etiqueta `<service>` dentro del nodo `<application>`
```
<application
    ... >
    ...
    <service
        android:name=".DownloadService"
        android:enabled="true"
        android:exported="true" >
    </service>

</application>
```

La etiqueta anterior contiene tres parámetros: **name** se refiere al nombre de la clase de implementación del servicio; **enabled** indica si es posible crear instancias del servicio; y **exported** establece si los componentes de otras apps pueden iniciar o interactuar con el servicio.

Puntos importantes a tener en cuenta:

* Usa el método `stopSelf()` dentro del servicio, para detener el servicio inmediatamente. Habrá ocasiones donde stopService() no pueda llamarse debido a que no sabemos el momento preciso en que el servicio terminará su trabajo.
* Un servicio se ejecuta por defecto en el hilo principal. Si usas trabajos pesados dentro de ellos, puede que recibas diálogos ANR. En estos casos debes usar hilos para no interferir.
* Termina los hilos iniciados dentro de `onDestroy()`. Si los dejas vivos, seguirán ejecutándose innecesariamente, si es que aún no han acabado su trabajo.
* Ejecutar un servicio más de una vez, crea varias instancias de este elemento, así que ten cuidado.
Si solo usarás un hilo para tu trabajo dentro del servicio, entonces usa la subclase `IntentService`, la cual maneja la creación de hilos por tí.

A continuación se muestan algunos ejemplos de servicios que pueden crearse en Android

## Crear Un IntentService

`IntentService` crea un hilo de trabajo para evitar interferencias en la UI. Una vez terminado dicho trabajo, el servicio es finalizado automáticamente.

A diferencia de `Service`, `IntentService` no ejecuta varias instancias al mismo tiempo si es llamado múltiples ocasiones.
Lo que haces es dejar en **cola** los **intents** que van llegando y así ejecutarlos de forma secuencial.
![Funcionamiento de los IntentService](http://www.hermosaprogramacion.com/wp-content/uploads/2015/07/iniciar-un-intent-service.png)

El trabajo será ejecutado dentro del método onHandleIntent() y no en onStartCommand(). Este método recibe como parámetro un intent que puede traer una acción asociada y parámetros que indiquen que decisión tomar.

```java 
@Override
protected void onHandleIntent(Intent intent) {
    if (intent != null) {
        // Acciones por realizar...
        }
    }
}
```

Si deseas crear un Intent Service en Android Studio ve a `File > New > Service > Intent Service`. Esto proveerá una clase base lista para extenderse.
![Crear IntentService](http://www.hermosaprogramacion.com/wp-content/uploads/2015/07/crear-servicio-en-android-studio.gif)

El código que generará Android Studio es el del ejemplo [WorkingIntentService](examples/WorkingIntentService.java)

¿Puntos a tener en cuenta?

- No es necesario sobrescribir onBind(). IntentService ya lo trae implementado con retorno null.
- Tampoco se debe llamar a stopSelf(), ya que el servicio se detiene automáticamente cuando despacha la cola de intents en proceso.
- Define constantes para acciones y parámetros que se asocien a los intents que recibe o envía el servicio. Con ello puedes decidir qué acción tomar dentro de onHandleIntent() y comunicar datos. Un ejemplo son las acciones FOO y BAR del Intent Service de arriba, las cuales traen un método personalizado de manejo tipo `handleAction*()`.


## Crear Un Bound Service

El enfoque de este artículo no es el uso de `bound services` o servicios ligados, ya que se utilizan en temas avanzados que aún no vamos a tocar. Sin embargo, podemos entender un poco la lógica de estos.

La idea es proveer acceso al servicio a otros componentes (clientes). Normalmente serán actividades. Para ello se usa la interfaz `IBinder` que retorna la instancia del servicio hacia el elemento que desea interactuar con él.

Ejemplo de Bound Service [JsonParsingService.java](examples/JsonParsingService.java)

El servicio anterior crea una clase interna llamada ParsingBinder, que extiende de Binder con el fin de retornar la instancia del servicio a través de getService().

Luego se crea una instancia de esta clase y se retorna en onBind(). Con esta convención, es posible acceder a la instancia del servicio.

En esta clase [DataActivity.java](examples/DataActivity.java) se muestra cómo acceder al servicio desde una actividad.

Desde el cliente solo iniciamos el servicio y luego lo atamos a la actividad con bindService().
`bindService()` recibe una instancia de la interfaz `ServiceConnection`, la cual monitorea el estado del servicio dentro de la aplicación.

Esta clase provee dos controladores: `onServiceConnected()` que se ejecuta cuando el servicio está listo y `onServiceDisconnected()` para indicar cuando el servicio quedó desligado.

## Poner Servicios En Primer Plano

Un servicio en primer plano tiene una prioridad crítica en Android. Tanto así, que el usuario no puede terminarlo manualmente hasta que se termine.

![Servicio en primer plano](http://www.hermosaprogramacion.com/wp-content/uploads/2015/07/foreground-service-android.png)

Para que esto sea posible, el servicio en primer plano debe proveer una notificación en la barra de estado que indique su existencia.

La única forma de quitarlo del primer plano es **terminándolo programáticamente** o cuando este finalice su trabajo.

El traslado a primer plano se lleva cabo con el método `startForeground()` dentro del servicio. Si deseas pasarlo a segundo plano de nuevo, entonces usas `stopForeground()`.

Un ejemplo de notificación sería para el servicio en primer plano
```java 
// Se construye la notificación
NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
        .setSmallIcon(android.R.drawable.stat_sys_download_done)
        .setContentTitle("Servicio en segundo plano")
        .setContentText("Procesando...");

// Crear Intent para iniciar una actividad al presionar la notificación
Intent notificationIntent = new Intent(this, SomeActivity.class);
PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);        
builder.setContentIntent(pendingIntent);

// Poner en primer plano
startForeground(1, builder.build());

// Acciones de proceso...
```

Luego del código anterior es posible que stopForeground() se ejecute en algún lugar por si se desea descartar la notificación. Es necesario que pases el valor de true como parámetro para hacerlo efectivo.

```java 
stopForeground(true);
```


# Ejemplo de un Servicios En Android

Para ver el ejemplo de Cómo crear un servicio en Android ver el siguiente archivo, dónde se especifícan los pasos para crear el servicio.
[ServiceAndroid.md](guides/ServiceAndroid.md)