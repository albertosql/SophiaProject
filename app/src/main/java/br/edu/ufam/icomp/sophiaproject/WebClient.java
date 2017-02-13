package br.edu.ufam.icomp.sophiaproject;


import android.util.Log;

import java.io.IOException;

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
    private OkHttpClient client = new OkHttpClient();
    private MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
    private Response response;

    public WebClient(String url, String json){
        this.url = url;
        this.json = json;
    }

    @Override
    public void run() {
        RequestBody body = RequestBody.create(mediaType, json);
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.post(body);

        Request request = builder.build();
        response = null;

        try {
            response = client.newCall(request).execute();
            idUser = response.body().string();
            Log.d("WEBCLIENT:", "INFO: Sinais enviados com Sucesso");
        } catch (IOException e) {
            Log.d("WEBCLIENT:", "ERROR: Error ao Realizar REQUEST ou RESPONSE");
        }
    }

}