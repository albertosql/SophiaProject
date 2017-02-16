package br.edu.ufam.icomp.sophiaproject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import static android.Manifest.*;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    int status = ActivityRecognized.status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //PERMISSÕES
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if(!checkIfAlreadyhavePermission()){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                101);
            }
        }

        if (ResultActivity.frase == R.string.sucess){
            Toast.makeText(getApplicationContext(), R.string.sucess, Toast.LENGTH_LONG).show();
        }else if (ResultActivity.frase == R.string.fail){
            Toast.makeText(getApplicationContext(), R.string.fail, Toast.LENGTH_LONG).show();
        }
    }

    public void ClickPlay(View view) throws InterruptedException {
        FileManager.kill();
        ActivityRecognized.status = 0;
        ResultActivity.frase = 0;
        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
        startActivity(intent);
    }

    public void ClickTutorial(View view){
        Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (101 == requestCode) {
            boolean granted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (granted) {
                Log.d("SUCCESS", "PERMISSÕES ACEITAS");
            } else {
                Log.d("ERROR", "PERMISSÕES RECUSADAS");
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            Log.d("ERROR", "CHAMADA DESCONHECIDA");
        }
    }

    private boolean checkIfAlreadyhavePermission() {
        int resultWES = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int resultRES = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        //int resultBS = ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS);

        if (resultWES == PackageManager.PERMISSION_GRANTED && resultRES == PackageManager.PERMISSION_GRANTED) {
            Log.d("INFO", "PERMISSÕES JÁ ATRIBUIDAS");
            return true;
        } else {
            Log.d("INFO", "PERMISSÕES NÃO ATRIBUIDAS");
            return false;
        }
    }
}