package br.edu.ufam.icomp.sophiaproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int result = ActivityRecognized.status;
        TextView resultado = (TextView)findViewById(R.id.textView_Result);
        String atividade;
        String idUser;

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
            atividade = "Parado";
        }else if (result == 2){
            resultado.setText(R.string.result2);
            atividade = "Andando";
        }else if (result == 3){
            resultado.setText(R.string.result3);
            atividade = "Correndo";
        }else if (result == 4){
            resultado.setText(R.string.result4);
            atividade = "Bicicleta";
        }else if (result == 5){
            resultado.setText(R.string.result5);
            atividade = "Dirigindo";
        }else{
            resultado.setText(R.string.result0);
            atividade = "NENHUMA";
        }

        //if (result != 0 ) {
            try {
                SharedPreferences sharedPref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
                idUser = sharedPref.getString("idUser", "");
                FileManager.getInstance().getSignals(idUser, atividade);
            } catch (IOException e) {
                Log.d("ERROR: ", "Erro ao Salvar Sinais.");
            }
        //}

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
