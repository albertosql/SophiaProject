package br.edu.ufam.icomp.sophiaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int result = ActivityRecognized.status;
        TextView resultado = (TextView)findViewById(R.id.textView_Result);

        /**
         * 0 para caso NÃO reconheça
         * 1 para PARADO
         * 2 para ANDANDO
         * 3 para CORRENDO
         * 4 para BIKE
         * 5 para DIRIGINDO
         */

        if (result == 1){
            resultado.setText(R.string.result1);
        }else if (result == 2){
            resultado.setText(R.string.result2);
        }else if (result == 3){
            resultado.setText(R.string.result3);
        }else if (result == 4){
            resultado.setText(R.string.result4);
        }else if (result == 5){
            resultado.setText(R.string.result5);
        }else{
            resultado.setText(R.string.result0);
        }

        Log.e("Status: ", "" + result);

    }

    public void ClickMenu(View view){
        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }

}
