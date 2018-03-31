# Definir un esquema y un contrato

Para más información ver [Cómo guardar datos en bases de datos SQL](https://developer.android.com/training/basics/data-storage/databases.html)

Una clase Contract es un contenedor para constantes que definen nombres de URI (identificadores uniformes de recursos), tablas y columnas. La clase Contract te permite utilizar las mismas constantes en todas las otras clases del mismo paquete. Esto te permite cambiar el nombre de una columna en un lugar y que ese cambio se propague en todo el código.

## 1. Creamos la entidad Abogado

Creamos un nuevo paquete llamado `data`. 
Dentro de `data` creamos la clase `Lawyer` con los siguientes atributos:
* id
* nombre
* especialidad
* número de teléfono
* biografía
* avatar

```java 
/**
 * Entidad "abogado"
 */
public class Lawyer {
    private String id;
    private String name;
    private String specialty;
    private String phoneNumber;
    private String bio;
    private String avatarUri;

    public Lawyer(String name,
                  String specialty, String phoneNumber,
                  String bio, String avatarUri) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.specialty = specialty;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
        this.avatarUri = avatarUri;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBio() {
        return bio;
    }

    public String getAvatarUri() {
        return avatarUri;
    }
}
```

## 2. Creamos el esquema de Lawyer

Dentro del paquete `data` creamos ahora una clase llamada `LawyersContract`, y definir una clase interna con los datos de de la tabla layer.

```java
/**
 * Esquema de la base de datos para abogados
 */
public class LawyersContract {

    public static abstract class LawyerEntry implements BaseColumns{
        public static final String TABLE_NAME ="lawyer";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String SPECIALTY = "specialty";
        public static final String PHONE_NUMBER = "phoneNumber";
        public static final String AVATAR_URI = "avatarUri";
        public static final String BIO = "bio";
    }
}
```


* Creamos la clase interna LawyerEntry para guardar el nombre de las columnas de la tabla.
* Se implementó la interfaz BaseColumns con el fin de agregar una columna extra que se recomienda tenga toda tabla.

Estas declaraciones facilitan el mantenimiento del esquema, por si en algún momento cambian los nombres de las tablas o columnas.