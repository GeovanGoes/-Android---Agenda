package br.comm.a4kontrol.ponto.modelo;

import java.io.Serializable;

/**
 * Created by geovan.goes on 12/05/2017.
 */
public class Configuracao implements Serializable {

    private String key;
    private String value;

    public Configuracao(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
