package br.comm.a4kontrol.ponto.modelo;

/**
 * Created by geovan.goes on 08/05/2017.
 */

public class Feriado {
    private int id;
    private String data;

    public Feriado() {
    }

    public Feriado(int id, String data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
