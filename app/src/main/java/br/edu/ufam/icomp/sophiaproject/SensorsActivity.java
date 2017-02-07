package br.edu.ufam.icomp.sophiaproject;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

public class SensorsActivity extends AppCompatActivity implements SensorEventListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    public GoogleApiClient mApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        //String device = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;
        //textViewDetail.setText(device);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mApiClient.connect();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Vibrator rr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                long milliseconds = 2500;
                rr.vibrate(milliseconds);

                Intent intent = new Intent(SensorsActivity.this, ResultActivity.class);
                startActivity(intent);
            }
        }, 15000);

        FileManager.getInstance().kill();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener((SensorEventListener) this);
        FileManager.getInstance().close();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Float x = event.values[0];
        Float y = event.values[1];
        Float z = event.values[2];

         /*
        Os valores ocilam de -10 a 10.
        Quanto maior o valor de X mais ele ta caindo para a esquerda - Positivo Esqueda
        Quanto menor o valor de X mais ele ta caindo para a direita  - Negativo Direita
        Se o valor de  X for 0 então o celular ta em pé - Nem Direita Nem Esquerda
        Se o valor de Y for 0 então o cel ta "deitado"
         Se o valor de Y for negativo então ta de cabeça pra baixo, então quanto menor y mais ele ta inclinando pra ir pra baixo
        Se o valor de Z for 0 então o dispositivo esta reto na horizontal.
        Quanto maioro o valor de Z Mais ele esta inclinado para frente
        Quanto menor o valor de Z Mais ele esta inclinado para traz.
        */

        FileManager.getInstance().append(x+","+y+","+z+"\n");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Intent intent = new Intent( this, ActivityRecognized.class );
        PendingIntent pendingIntent = PendingIntent.getService( this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates( mApiClient, 3000, pendingIntent );
        Log.e("Funcionou", "onConnected: ");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("Suspenso", "onConnectionSuspended");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("Failed", "onConnectionFailed: "+connectionResult.getErrorMessage());
    }

    public void kill() {
        Intent intent = new Intent( this, ActivityRecognized.class );
        PendingIntent pendingIntent = PendingIntent.getService( this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );
        ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates( mApiClient, pendingIntent );
        Log.e("REMOVEU !!!!!!!!!!!11", "KILL: ");
    }

    @Override
    public void onBackPressed() {

    }
}
