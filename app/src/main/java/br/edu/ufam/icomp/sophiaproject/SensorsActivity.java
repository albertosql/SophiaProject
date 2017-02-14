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
    private Sensor mGyroscope;
    private Sensor mAccelerometer;
    public GoogleApiClient mApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        String device = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mApiClient.connect();

        FileManager.getInstance().appendAcc(device  + "\n");
        FileManager.getInstance().appendGyr(device  + "\n");

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
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

        switch (event.sensor.getType()) {

            case Sensor.TYPE_ACCELEROMETER:

                Float x = event.values[0];
                Float y = event.values[1];
                Float z = event.values[2];

                FileManager.getInstance().appendAcc(x+","+y+","+z+"\n");
                break;

            case Sensor.TYPE_GYROSCOPE:
                Float a = event.values[0];
                Float b = event.values[1];
                Float c = event.values[2];

                FileManager.getInstance().appendGyr(a+","+b+","+c+"\n");
                break;
        }
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
