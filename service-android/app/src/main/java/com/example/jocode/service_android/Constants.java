package com.example.jocode.service_android;

/**
 * Contiene las constantes de las acciones de los servicios y sus parámetros
 */
public class Constants {

    /**
     * Constantes para {@link MemoryService}
     */
    public static final String ACTION_RUN_SERVICE = "com.example.jocode.service_android.memoryout.action.RUN_SERVICE";
    public static final String ACTION_MEMORY_EXIT = "com.example.jocode.service_android.memoryout.action.MEMORY_EXIT";

    public static final String EXTRA_MEMORY = "com.example.jocode.service_android.memoryout.extra.MEMORY";

    /**
     * Constantes para {@link ProgressIntentService}
     */
    public static final String ACTION_RUN_ISERVICE = "com.example.jocode.service_android.memoryout.action.RUN_INTENT_SERVICE";
    public static final String ACTION_PROGRESS_EXIT = "com.example.jocode.service_android.memoryout.action.PROGRESS_EXIT";

    public static final String EXTRA_PROGRESS = "com.example.jocode.service_android.memoryout.extra.PROGRESS";

}
