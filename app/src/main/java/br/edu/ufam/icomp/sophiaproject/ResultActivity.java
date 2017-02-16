package br.edu.ufam.icomp.sophiaproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private AlertDialog alerta;
    private String atividade;
    private String idUser;
    public static int frase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        int result = ActivityRecognized.status;
        TextView resultado = (TextView)findViewById(R.id.textView_Result);
        Button correto = (Button)findViewById(R.id.buttonCorreto);
        Button errado = (Button)findViewById(R.id.buttonErrado);

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
            correto.setClickable(true);
            errado.setVisibility(View.VISIBLE);
            errado.setText("Errado");
        }else if (result == 2){
            resultado.setText(R.string.result2);
            atividade = "Andando";
            correto.setClickable(true);
            errado.setVisibility(View.VISIBLE);
            errado.setText("Errado");
        }else if (result == 3){
            resultado.setText(R.string.result3);
            atividade = "Correndo";
            correto.setClickable(true);
            errado.setVisibility(View.VISIBLE);
            errado.setText("Errado");
        }else if (result == 4){
            resultado.setText(R.string.result4);
            atividade = "Bicicleta";
            correto.setClickable(true);
            errado.setVisibility(View.VISIBLE);
            errado.setText("Errado");
        }else if (result == 5){
            resultado.setText(R.string.result5);
            atividade = "Dirigindo";
            correto.setClickable(true);
            errado.setVisibility(View.VISIBLE);
            errado.setText("Errado");
        }else{
            resultado.setText(R.string.result0);
            atividade = "Nenhuma";
            correto.setClickable(false);
            errado.setVisibility(View.VISIBLE);
            errado.setText("Corrigir");

            //opcaoCorreta.setVisibility(View.VISIBLE);
        }

        Log.e("Status: ", "" + result);
    }

    public void ClickYes(View view){
        //enviarServidor();
        frase = R.string.sucess;
        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void ClickNo(View view){
        exemplo_lista_single();
    }

    @Override
    public void onBackPressed() {

    }

    private void exemplo_lista_single() {
        //Lista de itens
        final ArrayList<String> itens = new ArrayList<String>();
        itens.add("Parado");
        itens.add("Andando");
        itens.add("Correndo");
        itens.add("Andando de Bicicleta");
        itens.add("Andando de Carro");

        //adapter utilizando um layout customizado (TextView)
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_alerta, itens);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Qual atividade você fez?");
        //define o diálogo como uma lista, passa o adapter.
        builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(ResultActivity.this, "posição selecionada=" + arg1, Toast.LENGTH_SHORT).show();
                atividade = itens.get(arg1);
                alerta.dismiss();
                //enviarServidor();
                frase = R.string.fail;
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        alerta = builder.create();
        alerta.show();
    }

    public void enviarServidor(){

        //if (result != 0 ) {
        try {
            SharedPreferences sharedPref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
            idUser = sharedPref.getString("idUser", "");
            FileManager.getInstance().getSignals(idUser, atividade);
        } catch (IOException e) {
            Log.d("ERROR: ", "Erro ao Salvar Sinais.");
        }
        //}
    }
}