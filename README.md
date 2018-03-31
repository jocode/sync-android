# sync-android
Sincronización de base de datos SQLite local en android con MySQL a través de un servicio web

Este proyecto es basado de [¿Cómo Sincronizar Sqlite Con Mysql En Android?](http://www.hermosaprogramacion.com/2015/07/como-sincronizar-sqlite-con-mysql-en-android/)

La sincronización se debe realizar en un hilo separado de la UI, para evitar bloqueos en la App.
En este caso de usará Sync Adapters que son elementos destinados a correr de forma asíncrona las actualizaciones de nuestros datos, apoyándose del framework de sincronización de Android.
