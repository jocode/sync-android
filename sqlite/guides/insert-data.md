# Insertar información en la base de datos

El método para insertar registros en la base de datos es `SQLiteDatabase.insert()`.

Para agregar registros, debemos realizar lo siguiente:
1. Crea un objeto del tipo `ContentValues`. Este permite almacenar las columnas del registro en pares clave-valor
2. Añade los pares con el método put()
3. Invoca a insert() a través de la instancia de la base de datos

Los parámetros que recibe el método `insert()` son los siguiente:
* __String table__: Nombre de la tabla donde se insertará la info.
* __String nullColumnHack__: Nombre de una columna que acepta valores NULL y de la cual no se proveen pares clave-valor en values.
* __ContentValues values__: Conjunto de pares clave-valor para las columnas.

Los métodos `getWritableDatabase()` para obtener el manejador de la base de datos para operaciones de escritura. En cuestiones de lectura usa `getReadableDatabase()`
- `getWritableDatabase()` Para escribir o modificar sobre la base de datos 
- `getReadableDatabase()` Para leer información de la base de datos 


> Se puede usar el comando execSQL() para ejecutar una sentencia INSERT, pero como estás recibiendo datos externos, es mejor usar insert() para evitar inyecciones SQL.

```java 
@Override
public void onCreate(SQLiteDatabase db) {
    // Create table...

    // Contenedor de valores
    ContentValues values = new ContentValues();

    // Pares clave-valor
    values.put(LawyerEntry.ID, "L-001");
    values.put(LawyerEntry.NAME, "Carlos solarte");
    values.put(LawyerEntry.SPECIALTY, "Abogado penalista");
    values.put(LawyerEntry.PHONE_NUMBER, "300 200 1111");
    values.put(LawyerEntry.BIO, "Carlos es una profesional con 5 años de trayectoria...");
    values.put(LawyerEntry.AVATAR_URI, "carlos_solarte.jpg");

    // Insertar...
    db.insert(LawyerEntry.TABLE_NAME, null, values);

}
```



