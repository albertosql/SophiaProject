package br.edu.ufam.icomp.sophiaproject;


import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileManager {

    private static final String TAG = FileManager.class.getName();
    private static FileManager instance;

    private FileManager(){
        arquivoLogsAcc = "Sensors_logs_Accelerometer";
        arquivoLogsGyr = "Sensors_logs_Gyroscope";
        create();
    }

    public static FileManager getInstance(){

        instance = new FileManager();

        return instance;
    }

    private FileOutputStream arqCompletoAcc;
    private FileOutputStream arqCompletoGyr;
    private FileInputStream arqCompletoIn;
    private FileInputStream arqCompletoInGyr;
    private String arquivoLogsAcc;
    private String arquivoLogsGyr;
    private File diretorioCompletoAcc;
    private File diretorioCompletoGyr;



    //CRIA ARQUIVO .CSV
    private void create(){
        //CRIANDO O DIRETORIO
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Sophia");
        Log.d("PATH", f.getAbsolutePath());
        if (!f.exists()) {
            Log.d("MAKE DIR", f.mkdirs() + "");
        }

        try {
            String nomeArquivoAcc = arquivoLogsAcc + ".csv";
            String nomeArquivoGyr = arquivoLogsGyr + ".csv";

            diretorioCompletoAcc = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/Sophia"), nomeArquivoAcc);
            diretorioCompletoGyr = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/Sophia"), nomeArquivoGyr);

            arqCompletoAcc = new FileOutputStream(diretorioCompletoAcc, true);
            arqCompletoGyr = new FileOutputStream(diretorioCompletoGyr, true);

            arqCompletoIn = new FileInputStream(diretorioCompletoAcc);
            arqCompletoInGyr = new FileInputStream(diretorioCompletoGyr);

            //Log.d(TAG, "Todos os sensores criado com sucesso\n");
            String colunas = "x1, y1, z1\n";
            arqCompletoAcc.write(colunas.getBytes());
            arqCompletoAcc.flush();
            arqCompletoGyr.write(colunas.getBytes());
            arqCompletoGyr.flush();

        } catch (Exception e) {
            Log.e(TAG, "ERRO: erro em criar o arquivo completo: " + e.getMessage());
        }
    }

    public void appendAcc(String linha){
        try {
            this.arqCompletoAcc.write(linha.getBytes());
            this.arqCompletoAcc.flush();

            Log.d(TAG, "append: Nova linha ACC inserida");
        }catch (Exception e){
            Log.d(TAG, "ERRO: erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    public void appendGyr(String linha){
        try {
            this.arqCompletoGyr.write(linha.getBytes());
            this.arqCompletoGyr.flush();

            Log.d(TAG, "append: Nova linha Gyr inserida");
        }catch (Exception e){
            Log.d(TAG, "ERRO: erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    public void close(){
        try {
            this.arqCompletoAcc.close();
            this.arqCompletoGyr.close();
            //this.arqCompletoIn.close();
            //this.arqCompletoInGyr.close();
            Log.d(TAG, "close: Fechando o arquivo");
        }catch (Exception e){
            Log.e(TAG, "ERRO: erro ao fechar o arquivo completo: " + e.getMessage());
        }
    }

    //Envia os sinais gravados no arquivo para o servidor
    public void getSignals(String idUser, String atividade) throws IOException {
        try {
            BufferedReader acc = new BufferedReader(new InputStreamReader(arqCompletoIn));
            BufferedReader gyr = new BufferedReader(new InputStreamReader(arqCompletoInGyr));

            String strLineAcc = null;
            String strLineGyr = null;

            String json = "{\"id_user\":"+idUser+",\"time\":\"2016-12-14 23:49:00\", \"activity\":\""+atividade+"\",\"signal\":[";
            //Lendo linha por linha
            while ((strLineAcc = acc.readLine()) != null )  {
                String[] eixosAcc = strLineAcc.split(",");
                if((strLineGyr = gyr.readLine()) != null){
                    String[] eixosGyr = strLineGyr.split(",");
                    if (eixosGyr.length == 3) {
                        json += "{\"ax\":\"" + eixosAcc[0] + "\",\"ay\":\"" + eixosAcc[1] + "\",\"az\":\"" + eixosAcc[2] + "\", \"gx\":\"" + eixosGyr[0] + "\"," +
                                "\"gy\":\"" + eixosGyr[1] + "\", \"gz\":\"" + eixosGyr[2] + "\"},";
                    }else {
                        json += "{\"ax\":\""+eixosAcc[0]+"\",\"ay\":\""+eixosAcc[1]+"\",\"az\":\""+eixosAcc[2]+"\"},";
                    }

                }else{
                    json += "{\"ax\":\""+eixosAcc[0]+"\",\"ay\":\""+eixosAcc[1]+"\",\"az\":\""+eixosAcc[2]+"\"},";
                }
            }
            json = json.substring(0,json.length()-1);
            json +=  "]}";
            Thread webclient = new Thread (new WebClient("http://activitynator.esy.es/app.php/asignal", json), "Thread #1");
            webclient.start();
            webclient.join();
            Log.d(TAG, "INFO: Sinais enviados com sucesso ao servidor.");
        }catch (Exception e){
            Log.e(TAG, "ERRO: Erro ao enviar dados ao servidor: " + e.getMessage());
        }
        this.arqCompletoIn.close();
    }


    public static void kill(){
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Sophia");
        if (f.isDirectory())
        {
            String[] children = f.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(f, children[i]).delete();
            }
        }
    }
}
