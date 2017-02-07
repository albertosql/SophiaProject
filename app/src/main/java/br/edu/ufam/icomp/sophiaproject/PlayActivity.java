package br.edu.ufam.icomp.sophiaproject;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        final TextView instrucao = (TextView)findViewById(R.id.textView_Play);
        final ImageView imagem = (ImageView)findViewById(R.id.imageView_Play);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Vibrator rr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                long milliseconds = 2500;
                rr.vibrate(milliseconds);
                Intent intent = new Intent(PlayActivity.this, SensorsActivity.class);
                startActivity(intent);
            }
        }, 7000);
    }

    @Override
    public void onBackPressed() {

    }
}
