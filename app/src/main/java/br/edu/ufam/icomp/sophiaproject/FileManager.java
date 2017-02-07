package br.edu.ufam.icomp.sophiaproject;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Betinho on 02/02/2017.
 */

public class FileManager {

    private static final String TAG = FileManager.class.getName();
    private static FileManager instance;

    private FileManager(){
        arquivoLogs = "Sensors_logs";
        create();
    }

    public static FileManager getInstance(){
        if (instance == null){
            instance = new FileManager();
        }
        return instance;
    }

    private FileOutputStream arqCompleto;
    private String arquivoLogs;
    private File diretorioCompleto;



    //CRIA ARQUIVO .CSV
    private void create(){
        //CRIANDO O DIRETORIO
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Sophia");
        Log.d("PATH", f.getAbsolutePath());
        if (!f.exists()) {
            Log.d("MAKE DIR", f.mkdirs() + "");
        }

        try {
            String nomeArquivo = arquivoLogs + ".csv";
            diretorioCompleto = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/Sophia"), nomeArquivo);
            arqCompleto = new FileOutputStream(diretorioCompleto, true);

            //Log.d(TAG, "Todos os sensores criado com sucesso\n");
            String colunas = "x1, y1, z1\n";
            arqCompleto.write(colunas.getBytes());
            arqCompleto.flush();

        } catch (Exception e) {
            Log.e(TAG, "ERRO: erro em criar o arquivo completo: " + e.getMessage());
        }
    }

    public void append(String linha){
        try {
            this.arqCompleto.write(linha.getBytes());
            this.arqCompleto.flush();

            Log.d(TAG, "append: Nova linha inserida");
        }catch (Exception e){
            Log.d(TAG, "ERRO: erro em criar o arquivo completo: " + e.getMessage());
        }
    }

    public void close(){
        try {
            this.arqCompleto.close();
            Log.d(TAG, "close: Fechando o arquivo");
        }catch (Exception e){
            Log.e(TAG, "ERRO: erro ao fechar o arquivo completo: " + e.getMessage());
        }
    }

    public void kill(){
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Sophia");
        f.delete();
        Log.e("Deletou", "Deletou");
    }
}
