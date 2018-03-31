# Crear Base De Datos En SQLite

El SDK de Android nos permite administrar nuestro archivo de base de datos mediante una clase llamada `SQLiteOpenHelper`. Ésta clase abstracta nos provee de mecanismos básicos para la comunicación entre la aplicación Android y la información.

Para usar ese controlador debemos implementar algunas clases como:
- Crear una clase que extienda de SQLiteOpenHelper
- Configurar un constructor apropiado
- Sobrescribir los métodos onCreate() y onUpgrade()

## Creando Helpers Abogados

1. Creamos una nueva clase llamada `LawyersDbHelper`, que extienda de `SQLiteOpenHelper`.
2. Escribimos el constructor y usamos la palabra reservada `super` para mantener la herencia del Helper 
Los parámetros que recibe el constructor son los siguientes:
	- **Context context**: Contexto de acción para el helper.
	- **String name**: Nombre del archivo con extensión .db, donde se almacenará la base de datos, que a su vez corresponde al nombre de la base de datos.
	- **CursorFactory factory**: Asignamos null, por ahora no es necesario comprender el funcionamiento de este parámetro.
	- **int version**: Entero que representa la versión de la base de datos. Su valor inicial por defecto es 1. Si en algún momento la versión es mayor se llama al método onUpgrade() para actualizar la base de datos a la nueva versión. Si es menor, se llama a downUpgrade() para volver a una versión previa.

3. Sobreescribimos el método `onCreate()`:
Éste se llama automáticamente cuando creamos una instancia de la clase SQLiteOpenHelper. En su interior establecemos la creación de las tablas y registros.
Recibe como parámetro una referencia de la clase SQLiteDataBase, la cual actua como manejadora de la base de datos.

Por defecto la base de datos se guarda en:
> /data/data/<paquete>/databases/<nombre-de-la-bd>.db

4. Sobrescribe el método `onUpgrade()`

Este es ejecutado si se identificó que el usuario tiene una versión antigua de la base de datos.

En su interior establecerás instrucciones para modificar el esquema de la base de datos, como por ejemplo eliminar todo el esquema y recrearlo, agregar una nueva tabla, añadir una nueva columna, etc.

Recibe tres parámetros:
* __SQLiteDatabase db__: Manejador de la base de datos.
* __int oldVersion__: Se trata de un entero que indica la versión antigua de la base de datos.
* __int newVersion__: Entero que se refiere a la versión nueva de la base de datos.


```java 
public class LawyersDbHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Lawyers.db";

	public LawyersDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
	    // Comandos SQL
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    // No hay operaciones
	}

}
```

## Código SQL para crear una base de datos

Luego de crear el esquema, procederemos a crear la base de datos usando sentencias SQL usando el método `execSQL()`, del objeto `sqLiteDatabase` dentro del método `onCreate()`

```java 
@Override
public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL("CREATE TABLE " + LawyerEntry.TABLE_NAME + " ("
            + LawyerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + LawyerEntry.ID + " TEXT NOT NULL,"
            + LawyerEntry.NAME + " TEXT NOT NULL,"
            + LawyerEntry.SPECIALTY + " TEXT NOT NULL,"
            + LawyerEntry.PHONE_NUMBER + " TEXT NOT NULL,"
            + LawyerEntry.BIO + " TEXT NOT NULL,"
            + LawyerEntry.AVATAR_URI + " TEXT,"
            + "UNIQUE (" + LawyerEntry.ID + "))");

}
``` 

Es recomendable que la llave primaria sea BaseColumns._ID, ya que el framework de Android usa esta referencia internamente en varios procesos.
