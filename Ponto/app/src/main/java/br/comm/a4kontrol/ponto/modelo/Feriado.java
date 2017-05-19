package br.comm.a4kontrol.ponto.modelo;

import org.joda.time.DateTime;

import br.comm.a4kontrol.ponto.helper.DataHelper;

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

    public DateTime getDateTime(){
        String data = getData();
        int day = Integer.valueOf(data.split("-")[0]);
        int month = Integer.valueOf(data.split("-")[1]);
        int year = Integer.valueOf(data.split("-")[2]);

        DateTime dateTime = new DateTime(year,month,day,0,0);

        return dateTime;
    }
}
