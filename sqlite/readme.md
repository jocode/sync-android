# Qué es SQLite

Este tutorial ha sido tomado de [SQLite En Aplicaciones Android](http://www.hermosaprogramacion.com/2014/10/android-sqlite-bases-de-datos/)

Es un ligero motor de bases de datos de código abierto, que se caracteriza por mantener el almacenamiento de información persistente de forma sencilla.

A diferencia de otros Sistemas gestores de bases de datos como MySQL, SQL Server y Oracle DB, SQLite tiene las siguientes ventajas:

- No requiere el soporte de un servidor: SQLite no ejecuta un proceso para administrar la información, si no que implementa un conjunto de librerías encargadas de la gestión.
- No necesita configuración: Libera al programador de todo tipo de configuraciones de puertos, tamaños, ubicaciones, etc.
- Usa un archivo para el esquema: Crea un archivo para el esquema completo de una base de datos, lo que permite ahorrarse preocupaciones de seguridad, ya que los datos de las aplicaciones Android no pueden ser accedidos por contextos externos.
- Es de Código Abierto: Esta disponible al dominio público de los desarrolladores al igual que sus archivos de compilación e instrucciones de escalabilidad.

Es por eso que SQLite es una tecnología cómoda para los dispositivos móviles. Su simplicidad, rapidez y usabilidad permiten un desarrollo muy amigable.

## Ejemplo de SQLite en la aplicación Lawyers App 


+ **Lawyers**: Contiene una lista con todos los abogados del gabinete.
+ **Lawyer Detail**: Muestra el detalle de un abogado al presionar un ítem de lista.
+ **Add/Edit Lawyer**: Formulario con campos de texto para crear o modificar un abogado.


![Wireframe Lawyers App](http://www.hermosaprogramacion.com/wp-content/uploads/2016/07/wireframe-lawyers-user-interface-android-911x1300.png)


## Pasos para la creación de la Aplicación y la BD SQLite

1. [Definir un esquema y un contrato](guides/scheme-contract.md)
2. [Crear Base De Datos En SQLite](guides/create-sqlite-db.md)
3. [Insertar información en la base de datos](guides/insert-data.md)
4. [Leer información de la base de datos](guides/read-data.md)

