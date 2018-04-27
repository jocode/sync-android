
# Ejemplo de Servicios en Android

Ahora se creará un ejemplo de Servicios en Android, en una aplicación donde el primer servicio monitorea la memoria disponible en el servicio incluso si la aplicación está en segundo plano. El otro es un `IntentService` (Crea hilos separados para evitar interferencias con la UI), que simula un proceso de 10 segundos y muestra una barra de progreso que se quita al terminar el servicio.

Ahora vamos a crear un nuevo proyecto.

Para eso Vamos a **Android Studio** y creamos un nuevo proyecto con una **Blank Activity**

Luego modificamos el *style* de la aplicación en `res/values/styles.xml`, y para eliminar la `action bar` de nuestra actividad usamos el estilo padre `Theme.AppCompat.NoActionBar`.

Configuramos el estilo del tema y agregamos las Constantes que se usarán en al aplicación para leer el uso de memoria de nuestro dispositivo.

> Creamos el diseño de la actividad. Que contiene un LinerLayout con dos elementos hijos que se reparten con el mismo peso en la pantalla.

Cada servicio tendrá dos acciones asociadas. Una para indicar que está corriendo (RUN) y otra para indicar que ya terminó (EXIT).

Debido a que deseamos mandar los valores desde los servicios a las acciones para notificarlos en los text views de la actividad, necesitamos extras para obtener los valores.

En este caso EXTRA_MEMORY se refiere a la cantidad de memoria disponible que se notificará. Y EXTRA_PROGRESS al progreso que se da en el segundo servicio.

## Agregamos la clase MemoryService
La idea es repetir una misma tarea cada segundo, para obtener la memoria disponible en el sistema.

Recuerda que la clase Timer nos permite realizar una tarea del tipo TimerTask repetidas ocasiones. En este caso deseamos hacerlo hasta que el usuario decida parar el servicio. Para ello se usa el método `scheduleAtFixedRate()`, donde el tercer parámetro es el tiempo entre ejecuciones.

La clase la creamos en [MemoryService.java](../app/src/main/java/com/example/jocode/service_android/MemoryService.java)

La información sobre la memoria se obtiene a través de MemoryInfo.

Primero obtén el ActivityManager a través de getSystemService() con la constante ACTIVITY_SERVICE.

Luego pides la información con  ActivityManager.getMemoryInfo() y se la asignas al objeto MemoryInfo. Con esto ya puedes consultar el atributo availMem, el cual contiene el dato de la memoria disponible actual.

Pero… ¿Cómo emitir el intent y la memoria disponible?

A través del LocalBroadcastManager. Este elemento emite un mensaje en forma de intent hacia los elementos de nuestra aplicación con el método sendBroadcast().

Importante resaltar que en onDestroy() cancelamos la timer task, ya que no deseamos que se ejecute indefinidamente. Además emitimos la acción de terminación del servicio para que la actividad tome la decisión necesaria.

## Crear el IntentService
Gestionará el proceso de conteo en primer plano. Añade una clase llamada ProgressIntentService.java e incluye el código de abajo:

La clase [ProgressIntentService.java](../app/src/main/java/com/example/jocode/service_android/ProgressIntentService.java)

Esta vez incluimos las acciones en onHandleIntent() como vimos en los apartados anteriores.

Dentro de este método comprobamos que la acción del intent sea Constants.ACTION_RUN_ISERVICE. Si es así, entonces iniciamos el método handleActionRun().

handleActionRun() se encarga de:

* Crear una notificación para mostrar cuando se invoque startForeground().
* Iniciar un bucle que simula una operación que tarda 10 segundos en terminar.
* Actualizar el progreso de la notificación con setProgress()
* Enviar el progreso hacia la actividad a través del LocalBroadcastManager.
* Quitar el servicio de primer plano con stopForeground() al terminar el bucle.

Dentro de onDestroy() se envía la señal de terminación del servicio.

## Declarar los Servicios en el Manifest

Debemos declarar los sevicios en el Manifest de la aplicación.
```
<service
    android:name=".MemoryService"
    android:enabled="true"
    android:exported="true" >
</service>
<service
    android:name=".ProgressIntentService"
    android:exported="false" >
</service>
```

## Declarar las Acciones de los botones para ejecutar los Servicios

Por último se deben poner a correr los servicios cuando los botones sean presionados. Esto se lleva a cabo con `startService()` dentro de las respectivas escuchas.

Ten en cuenta que para recibir los intents emitidos desde los servicios es necesario registrar un `BroadcastReceiver` asociado a la actividad.

Este elemento se comporta como una especie de antena que recibe señales, que si se ajusta a las frecuencias necesarias (acción del intent), podrá recibir información de otros componentes.

La creación del BroadcastReceiver, la hacemos en [MainActity.java](../app/src/main/java/com/example/jocode/service_android/MainActivity.java)

### Qué hacer para que la Actividad reciba los mensajes


1. Declarar un BroadcastReceiver interno y sobreescribir el controlador `onReceive()`. Dentro de él usar un switch para tomar decisiones basado en las acciones del intent entrante.
2. Dentro de `onCreate()` instanciar un IntentFilter, el cual contiene las acciones por las que estaremos pendientes.
3. Crear una nueva instancia del broadcast receiver y luego registrar su presencia con el método `LocalBroadcastManager.registerReceiver()`, asociando los filtros que deseamos que coincidan con las señales entrantes.

## Conclusión

En este ejemplo se mostró como crear servicios iniciados para correr tareas en segundo plano. También vimos como enviar un servicio en primer plano hasta que termine su cometido.

Los servicios nos permitirán construir aplicaciones más avanzadas que provean mejores características a nuestros usuarios y que mantenga la integridad de la información.
