package br.comm.a4kontrol.ponto.modelo;

import org.joda.time.DateTime;

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

    public DateTime getDateTime(){
        String data = getData();
        int day = Integer.valueOf(data.split("-")[0]);
        int month = Integer.valueOf(data.split("-")[1]);
        int year = Integer.valueOf(data.split("-")[2]);

        String horario = getHorario();
        int hour = Integer.valueOf(horario.split(":")[0]);
        int min = Integer.valueOf(horario.split(":")[1]);
        DateTime dateTime = new DateTime(year,month,day,hour,min);

        return dateTime;
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
