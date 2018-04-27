import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

public class DataActivity extends AppCompatActivity {
    Intent serviceIntent;
    JsonParsingService parsingService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        serviceIntent = new Intent(this, JsonParsingService.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Iniciar el servicio
        startService(serviceIntent);
        // Atar el servicio a la actividad
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            parsingService = ((JsonParsingService.ParsingBinder) service).getService();
            // Acciones...
        }

        public void onServiceDisconnected(ComponentName className) {
            parsingService = null;
            // Acciones...
        }
    };
}