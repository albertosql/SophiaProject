package br.edu.ufam.icomp.sophiaproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    int status = ActivityRecognized.status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
