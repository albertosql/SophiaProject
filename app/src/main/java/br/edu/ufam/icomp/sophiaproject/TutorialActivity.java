package br.edu.ufam.icomp.sophiaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        TextView tutorial = (TextView)findViewById(R.id.textView_Talk);
        TextView tutorial2 = (TextView)findViewById(R.id.textView_Talk_2);
        ImageView next = (ImageView)findViewById(R.id.imageView_Next);
    }

    public void ClickNext(View view){
        TextView tutorial = (TextView)findViewById(R.id.textView_Talk);
        TextView tutorial2 = (TextView)findViewById(R.id.textView_Talk_2);
        ImageView sophia = (ImageView)findViewById(R.id.imageView_tutorial);
        ImageView prev = (ImageView)findViewById(R.id.imageView_previous);
        ImageView next = (ImageView)findViewById(R.id.imageView_Next);

        if (tutorial2.getText().equals("")) {
            tutorial.setText(R.string.tutorial2);
            tutorial2.setText(R.string.tutorial3);
            prev.setImageResource(R.drawable.prev);
            sophia.setImageResource(R.drawable.celular_bolso);
        }else if (tutorial2.getText().equals(getResources().getString(R.string.tutorial3))){
            tutorial2.setText(R.string.tutorial4);
            sophia.setImageResource(R.drawable.atividades);
        }else if (tutorial2.getText().equals(getResources().getString(R.string.tutorial4))){
            tutorial2.setText(R.string.tutorial5);
            sophia.setImageResource(R.drawable.celular_fora_bolso);
            next.setImageResource(R.drawable.go);
        }else if (tutorial2.getText().equals(getResources().getString(R.string.tutorial5))){
            Intent intent = new Intent(TutorialActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void ClickPrevious(View view){
        TextView tutorial = (TextView)findViewById(R.id.textView_Talk);
        TextView tutorial2 = (TextView)findViewById(R.id.textView_Talk_2);
        ImageView sophia = (ImageView)findViewById(R.id.imageView_tutorial);
        ImageView prev = (ImageView)findViewById(R.id.imageView_previous);
        ImageView next = (ImageView)findViewById(R.id.imageView_Next);

       if (tutorial.getText().equals(getResources().getString(R.string.tutorial1))){
           Intent intent = new Intent(TutorialActivity.this, MainActivity.class);
           startActivity(intent);
       } else if (tutorial2.getText().equals(getResources().getString(R.string.tutorial3))){
            tutorial.setText(R.string.tutorial1);
            tutorial2.setText("");
            prev.setImageResource(R.drawable.back);
           sophia.setImageResource(R.drawable.ball2);
       }else if (tutorial2.getText().equals(getResources().getString(R.string.tutorial4))){
           tutorial2.setText(R.string.tutorial3);
           sophia.setImageResource(R.drawable.celular_bolso);
       }else if (tutorial2.getText().equals(getResources().getString(R.string.tutorial5))){
           tutorial2.setText(R.string.tutorial4);
           sophia.setImageResource(R.drawable.atividades);
           next.setImageResource(R.drawable.next2);
       }
    }

    @Override
    public void onBackPressed() {

    }
}
