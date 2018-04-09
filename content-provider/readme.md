# Content Provider

## ¿Qué es un Proveedor de Contenido?
Un proveedor de contenido es una interfaz diseñada para compartir datos con otras aplicaciones, pero de manera encapsulada, solo ofrece los datos, no algún procedimiento interno de la aplicación origen o destino

Este contenido está basado del tutorial [Crear Un Content Provider Personalizado](http://www.hermosaprogramacion.com/2015/06/tutorial-android-crear-un-content-provider-personalizado/) de http://www.hermosaprogramacion.com.

**¿Qué veremos en este ejemplo?**

* Aprender a crear un content Provider
* Usar métodos de consulta de datos, inserción, modificación y eliminación a través de un Content Resolver.
* Establecer tipos MIME para tus datos, crear URIs de contenido, generar los permisos necesarios de acceso


**Content Provider** es un componente de una aplicación Android que actúa como una envoltura de seguridad para los datos de una aplicación. Esto permite compartir sus datos estructurados con otras aplicaciones.
![Estructura Content Provider](http://www.hermosaprogramacion.com/wp-content/uploads/2015/06/arquitectura-l%C3%B3gica-de-un-content-provider.png)

Prácticamente es una clase que encapsula tu implementación SQLite a través de un mecanismo de códigos secretos (URIs) para que otras aplicaciones no sepan nada sobre su estructura. Sin embargo se proporcionan las modalidades necesarias para consultar, insertar, eliminar y actualizar los datos.

Existe une mediador llamado **Content Resolver**

## ¿Qué es el Content Resolver?

Es el encargado de acceder a los content providers que se encuentran en las aplicaciones de tu dispositivo Android. Su trabajo es revisar la URI de contenido que se le brinda. Con ese dato realiza una búsqueda para llegar hasta la base de datos especificada.

Por esa razón siempre usarás un Content Resolver para consultar, insertar, modificar y eliminar los datos de un Content Provider.


En este ejemplo se creará un Content Provider para una base de datos que contiene los registros de las actividades de llos técnicos en una empresa. Este es un paso importante a realizar, a la hora de trabajar sincronización de datos con un servidor.

Pasos para crear el Content Provider
- [ ] Crear una Api pública (clase Contract) para el consumo de datos
- [ ] Implementar los métodos del content provider
- [ ] Registrar el content provider en el Android Manifest


El diseño conceptual de la base de datos, se basa en los siguientes campos.
* __\_id__: Es el identificador de cada actividad
* __descripción__: Breve descripción que especifica la acción a realizar
* __categoría__: Es la etiqueta que emplea la empresa para clasificar los diferentes tipos de actividad
* __estado__: Representa el curso actual de la actividad. Pueden ser los siguientes valores: “Cerrada”, “En Curso”, “Abierta”.
* __tecnico__: Funcionario al que se asignará la actividad.

## Creación del Content Provider Personalizado

1. [Crear API pública para el consumo de datos](guides/1.Public-api.md)
2. [Implementar Métodos Del Content Provider](guides/2.Methods-contend-provider.md)
3. [Registrar el Content Provider en el Android Manifest](guides/3.Register-content-provider-in-android.md)
4. [Uso de los Loader Callbacks](guides/4.LoaderCallbacks.md)



En resumen para la creación de proveedores de contenidos debemos hacer estos pasos:

- Crear la clase Contrato y definir las URIs y MIMEs del Content Provider
- Crear la Clase Provider `TechsProvider` que extienda de `ContentProvider` e implementar los métodos
- Crear la clase gestora de SQLite que extienda de SQLiteOpenHelper y definir los métodos y sentencias DDL (Data Definition Language) para las tablas.
- Creación de las funcionalidades en los métodos de la clase Provider `TechsProvider`
- Registrar el Content Provider en el Android Manifest
- Crear las interfaces y la funcionalidades de la aplicaciones para Usar El Content Provider Personalizado
- Uso de los `LoaderCallbacks` para la carga de datos

