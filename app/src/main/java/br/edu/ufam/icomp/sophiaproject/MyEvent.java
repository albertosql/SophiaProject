package br.edu.ufam.icomp.sophiaproject;

/**
 * Created by Betinho on 15/02/2017.
 */
public class MyEvent {
    private String atividade;

    public MyEvent(String atividade) {
        this.atividade = atividade;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }
}
