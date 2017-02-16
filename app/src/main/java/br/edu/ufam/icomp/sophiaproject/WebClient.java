package br.edu.ufam.icomp.sophiaproject;


import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by fabricio on 08/02/17.
 */

public class WebClient implements Runnable{
    static String idUser;
    private String json;
    private String url;
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS).build();
    private MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
    private Response response;

    public WebClient(String url, String json){
        this.url = url;
        this.json = json;
    }

    @Override
    public void run() {
        Log.d("WEBCLIENT", "INFO: INCIANDO O ENVIO PARA O SERVIDOR!");
        RequestBody body = RequestBody.create(mediaType, json);

        Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();

        try {
            response = client.newCall(request).execute();
            idUser = response.body().string();
            Log.d("WEBCLIENT", "INFO: SINAIS ENVIADOS AO SERVIDOR COM SUCESSO!");
        } catch (IOException e) {
            Log.d("WEBCLIENT", "ERROR: ERRO AO OBTER REQUEST/RESPONSE");
        }
    }

}