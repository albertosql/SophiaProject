package br.edu.ufam.icomp.sophiaproject;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

public class ActivityRecognized extends IntentService {
    private static final String TAG = ActivityRecognized.class.getName();
    private int PRECISAO = 65;
    public static int status;

    public ActivityRecognized() {
        super("ActivityRecognizedService");
    }

    public ActivityRecognized(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities( result.getProbableActivities() );
        }
    }

    private void handleDetectedActivities(List<DetectedActivity> probableActivities) {
        for( DetectedActivity activity : probableActivities ) {
            switch( activity.getType() ) {
                case DetectedActivity.IN_VEHICLE: {
                    Log.e( TAG, "VEICULO: " + activity.getConfidence() );
                    if( activity.getConfidence() >= PRECISAO ) {
                        status = 5;
                    }
                    break;
                }
                case DetectedActivity.ON_BICYCLE: {
                    Log.e(TAG, "BIKE: " + activity.getConfidence() );
                    if( activity.getConfidence() >= PRECISAO ) {
                        status = 4;
                    }
                    break;
                }
                case DetectedActivity.ON_FOOT: {
                    Log.e( TAG, "FOOT: " + activity.getConfidence() );
                    if( activity.getConfidence() >= PRECISAO ) {
                        status = 2;
                    }
                    break;
                }
                case DetectedActivity.RUNNING: {
                    Log.e( TAG, "Running: " + activity.getConfidence() );
                    if( activity.getConfidence() >= PRECISAO) {
                        status = 3;
                    }
                    break;
                }
                case DetectedActivity.STILL: {
                    Log.e( TAG, "PARADO: " + activity.getConfidence() );
                    if( activity.getConfidence() >= PRECISAO ) {
                        status = 1;
                    }
                    break;
                }
                case DetectedActivity.TILTING: {
                    Log.e(TAG, "Tilting: " + activity.getConfidence() );
                    break;
                }
                case DetectedActivity.WALKING: {
                    Log.e(TAG, "WALKING: " + activity.getConfidence() );
                    if( activity.getConfidence() >= PRECISAO ) {
                        status = 2;
                    }
                    break;
                }
                case DetectedActivity.UNKNOWN: {
                    Log.e( TAG, "NAO SEI: " + activity.getConfidence() );
                    if( activity.getConfidence() >= PRECISAO ) {
                        status = 0;
                    }
                    break;
                }
            }
        }
    }


}