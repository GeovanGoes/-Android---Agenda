package br.comm.a4kontrol.ponto.modelo;

import java.io.Serializable;

/**
 * Created by geovan.goes on 08/05/2017.
 */

public class Lancamento implements Serializable{

    private int id;
    private String horario;
    private String data;

    public Lancamento() {
    }

    public Lancamento(int id, String horario, String data) {
        this.id = id;
        this.horario = horario;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
