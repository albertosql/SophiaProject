package br.edu.ufam.icomp.sophiaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ClickPlay(View view){
        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
        startActivity(intent);
    }

    public void ClickTutorial(View view){
        Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }

}
