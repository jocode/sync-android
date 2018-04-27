import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class JsonParsingService extends Service {

    private static final String LOGTAG = JsonParsingService.class.getSimpleName();


    @Override
    public void onCreate() {
        Log.i(LOGTAG, "Tracking Service Running...");
    }

    @Override
    public void onDestroy() {
        Log.i(LOGTAG, "Tracking Service Stopped...");
    }


    /* Método de acceso */
    public class ParsingBinder extends Binder {
        JsonParsingService getService() {
            return JsonParsingService.this;
        }
    }

    private final IBinder binder = new ParsingBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}