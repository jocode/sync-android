# Desarrollar Aplicación Android

## Preparar Proyecto En Android Studio

### Paso 1
 Abre Android Studio y ve a `File > New > New Project` para crear un nuevo proyecto. Asignale un nombre y luego elige una actividad en blanco (Blank Activity).

### Paso 2
 Se usarán varios componentes definidos por comportamiento, se crearán los siguientes paquetes Java para tener una división de las funcionalidades más destacada:
 ![Estructura Crunch Expenses](http://www.hermosaprogramacion.com/wp-content/uploads/2015/07/estructura-de-proyecto-en-android-studio.png)

 La información que contendrá cada paquete será el siguiente:
* __provider:__ Como su nombre lo indica, tendrá la estructura del Content Provider para la encapsulación de la base de datos Sqlite.
* __sync:__ Contiene las piezas para la autenticación y la sincronización.
* __ui:__ contiene los elementos relacionados con la interfaz de usuario como lo son las actividades.
* __utils:__ Todas aquellas clases que aíslen constantes y métodos comunes.
* __web:__ Aquí incluiremos las clases de conexión a la red como lo es Volley.

### Paso 3
Añadir las siguientes dependencias funcionales al archivo build.gradle:
```
dependencies {
    ...
    compile 'com.android.support:appcompat-v7:22.2.0'
    compile 'com.android.support:design:22.2.0'
    compile 'com.android.support:recyclerview-v7:22.2.0'
    compile 'com.mcxiaoke.volley:library:1.0.16'
    compile 'com.google.code.gson:gson:2.3.1'
}
```

> Estas librerías se deben actualizar de acuerdo a la versión del SDK que estén trabajando actualmente

### Paso 4
Preparar los strings que vamos a usar en los layouts y en la app en general. Ver el archivo [strings.xml](../app/src/main/res/values/strings.xml)

### Paso 5
Crear y abir el archivo [colors.xml](../sync-android/app/src/main/res/values/colors.xml) para establecer los colores de tu esquema en material design:

### Paso 6
El siguiente paso para preparar el proyecto es añadir las dimensiones y medidas que habrá en los layouts. Ver el archivo [dimens.xml](../sync-android/app/src/main/res/values/dimens.xml)

## Paso 7
Ahora es turno de crear la carpeta `transition-v21` para añadir un archivo llamado explode.xml. Por simplicidad solo incorporaremos un elemento <explode> para usar en las [transiciones entre actividades](http://www.hermosaprogramacion.com/2015/07/usar-transiciones-en-android-con-material-design/).

Para crear el directorio `transition-v21` debemos dar clic derecho sobre la carpeta `res -> new -> Android Resourse Directory`, y colocar el nombre `transition-v21`. Luego crear el archivo `explode.xml`.

## Paso 8
Finalizando esta parte, modificaremos los estilos de la aplicación. Para el archivo genérico `/res/values/styles.xml` añade la siguiente definición: Ver el archivo [styles.xml](../app/src/main/res/values/styles.xml)

Crea una nueva variación de los estilos en la versión 21 y agrega el soporte de las transiciones: Ver el archivo [styles.xml(v21)](../app/src/main/res/values-v21/styles.xml)


Luego de tener configurado el proyecto en su forma más general, continuamos con la creación del [Creación De Content Provider](4.Creacion-content-provider.md).


