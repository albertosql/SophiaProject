package br.edu.ufam.icomp.sophiaproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    String idFacebook, email, altura, peso, model_phone;
    Integer idade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bundle bundle = getIntent().getExtras();
        String birthday = "24091997";

        int mes = Integer.parseInt(birthday.substring(0, 2));
        int dia = Integer.parseInt(birthday.substring(2, 4));
        int ano = Integer.parseInt(birthday.substring(4));

        idFacebook = bundle.getString("id");
        email = bundle.getString("email");
        idade = getAge(ano, mes, dia);
        model_phone = android.os.Build.MODEL;
    }

    public void ClickCadastro(View v){
        EditText alturaEditText = (EditText)findViewById(R.id.altura);
        EditText pesoEditText = (EditText)findViewById(R.id.peso);

        if(pesoEditText.getText().toString().trim().equals("") || alturaEditText.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), "Um dos campos está vazio!", Toast.LENGTH_SHORT).show();
        }

        peso = pesoEditText.getText().toString();
        altura = alturaEditText.getText().toString();


        String json = "{\"id_facebook\":\""+idFacebook+"\",\"age\":\""+idade+"\",\"height\":\""+altura+"\"," +
                "\"weight\":\""+peso+"\",\"model_phone\":\""+model_phone+"\"}";


        Thread webclient = new Thread (new WebClient("http://activitynator.esy.es/app.php/user", json), "Thread #1");
        webclient.start();
        try {
            webclient.join();
            Log.d("Register Activity", "ClickRegistrar: Dados de usuário enviado com sucesso.");
        } catch (InterruptedException e) {
            Log.d("Register Activity", "ClickRegistrar: Erro ao Enviar dados de usuário.");
        }
        String idUser = WebClient.idUser;
        idUser = idUser.split(":")[1];
        idUser = idUser.substring(0,idUser.length()-1);

        //SharedPreferences
        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences("USER_DATA",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("idFacebook", String.valueOf(idFacebook));
        editor.putString("age", String.valueOf(idade));
        editor.putString("height", String.valueOf(altura));
        editor.putString("weight", String.valueOf(peso));
        editor.putString("model_phone", String.valueOf(model_phone));
        editor.putString("idUser", String.valueOf(idUser));

        editor.commit();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);

    }

    private Integer getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(year, month, day);
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }
        Integer ageInt = new Integer(age);
        return ageInt;
    }
}
