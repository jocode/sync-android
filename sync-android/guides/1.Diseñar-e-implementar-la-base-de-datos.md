# Crear Servicio Web Con Php Y Mysql

## 1. Diseñar E Implementar La Base De Datos
En este ejemplo sólo se trabajará con una entidad, la cual es la siguiente:

![Diagrama entidad relación](http://www.hermosaprogramacion.com/wp-content/uploads/2015/07/diagrama-entidad-relacion-gastos.png)

Básicamente podemos describir un gasto por el monto de dinero usado, la etiqueta que el usuario pueda darle, como gastos de diversión, por comida, salud, etc.

Importante la fecha en que se realizó y alguna descripción que indique más aspectos sobre el gasto.

> En este caso usaremos una herramienta llamada XAMPP, que no gestionará  el servidor Apache, PHP y MySQl.

1. Creamos una base de datos llamda `crunch_expenses`. Y luego creamos la tabla gasto.
```sql
CREATE TABLE gasto(
 idGasto INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
 monto INT UNSIGNED NOT NULL,
 etiqueta VARCHAR(25) NOT NULL,
 fecha DATE NOT NULL,
 descripcion VARCHAR(125)
 )
```

Luego añadimos cinco (5) registros a la tabla `gasto`. (Los datos son de prueba).
```sql
INSERT INTO gasto(idGasto, monto, etiqueta, fecha, descripcion)
VALUES 
(NULL, 12, 'Transporte', '2015/07/01', 'Viaje al centro'),
(NULL, 200, 'Comida', '2015/06/15', 'Mercado mensual'),
(NULL, 100, 'Diversion', '2015/07/02', 'Salida a cine'),
(NULL, 25, 'Transporte', '2015/07/04', 'Compra de gasolina'),
(NULL, 5, 'Comida', '2015/07/06', 'Almuerzo de negocios');
```


## 2. Implementar la Conexión a la base de Datos

En este caso usaremos nuestro editor favorito. Creamos un proyecto nuevo con dos carpetas:

* __data__ Contendrá los archivos de conexión a base de datos
* __web__ Contendrá los archivos de operación de datos

1. Dentro de `data` añadir un nuevo archivo llamado `mysql_login.php`. Su objetivo es contener las `constantes` para definir:
- Nombre del host
- Puerto de conexión
- Nombre de la base de datos a la cual accederemos
- Nombre de usuario
- Contraseña.


2. Ahora añade un nuevo archivo con el nombre de `DatabaseConnection.php` en la carpeta `data`. Incluiremos la clase que encierre una instancia de PDO con un `patrón singleton`. De esta manera podremos usar la conexión en cualquier lugar.

3. Crear una clase que facilite la operación de los registros en la base de datos. Crea un archivo llamado `Gastos.php`.

Esta clase implementa la conexión a la base de datos para producir los métodos `getAll()` e `insertRow()`.

`getAll()` se encarga de retornar todos los gastos que hay en la base de datos a través de un comando SELECT.

`insertRow()` añade un nuevo registro a la base de datos basado en un array como parámetro de entrada. Super *importante retornar en el id remoto del registro recién insertado* a través del método `lastInsertId()`, ya que *en la base de datos de la aplicación Android se requerirá una copia de este valor*.

```
crunch_expenses/
├── data/
│	├── mysql_login.php
│	└── DatabaseConnection.php
└── web/
	├──	Gastos.php
	├──	obtener_gastos.php
	└── insertar_gasto.php
	
```

## 3. Implementar Métodos De Operación De Datos

4. Dentro del directorio `web` añade un archivo con el nombre de `obtener_gastos.php`. Esta implementación buscará leer solo las peticiones `GET` desde el cliente para retornar en todos los registros de la base de datos Mysql.

Recuerda que usamos el método `json_encode()` para formatear el array de gastos en un objeto Json equivalente. Si obtuviéramos los datos actuales de la base de datos, la respuesta sería esta:

```json 
{
    "estado":1,
   "gastos":[
      {
         "idGasto":"30",
         "monto":"12",
         "etiqueta":"Transporte",
         "fecha":"2015-07-01",
         "descripcion":"Viaje al centro"
      },
      {
         "idGasto":"31",
         "monto":"200",
         "etiqueta":"Comida",
         "fecha":"2015-06-15",
         "descripcion":"Mercado mensual"
      },
      {
         "idGasto":"32",
         "monto":"100",
         "etiqueta":"Diversion",
         "fecha":"2015-07-02",
         "descripcion":"Salida a cine"
      },
      {
         "idGasto":"33",
         "monto":"25",
         "etiqueta":"Transporte",
         "fecha":"2015-07-04",
         "descripcion":"Compra de gasolina"
      },
      {
         "idGasto":"34",
         "monto":"5",
         "etiqueta":"Comida",
         "fecha":"2015-07-06",
         "descripcion":"Almuerzo de negocios"
      },
      {
         "idGasto":"35",
         "monto":"100",
         "etiqueta":"Diversi\u00f3n",
         "fecha":"2015-07-23",
         "descripcion":"Salida a cine"
      },
      {
         "idGasto":"37",
         "monto":"288",
         "etiqueta":"Comida",
         "fecha":"2015-07-15",
         "descripcion":"Mercado mensual "
      },
      {
         "idGasto":"38",
         "monto":"2000",
         "etiqueta":"Diversi\u00f3n",
         "fecha":"2015-07-20",
         "descripcion":"Salida a san andres"
      }
   ]
}
```

De lo contrario sería:

```json 
{
   "estado":1,
   "mensaje":"Ha ocurrido un error"
}
```

5. Ahora añade el archivo que manejará el método `POST` para la inserción de nuestros gastos. Crea un nuevo archivo llamado `insertar_gasto.php`.

Este archivo espera recibir un objeto Json en el cuerpo de la petición con los datos de un gasto. Esa es la razón del porque usamos `json_decode()` para extraer la información.

Si la inserción es válida, entonces incluiremos en la respuesta el identificador remoto del registro, por lo que añadimos un tercer atributo para ello. Una respuesta de éxito típica sería:

```json 
{
    "estado":1,
   "mensaje":"Creación éxitosa",
   "idGasto":"2"
}
```

O de lo contrario tendríamos:

```json 
{
    "estado":2,
   "mensaje":"Creación fallida"
}
```


