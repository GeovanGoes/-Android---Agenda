package com.example.geovan.ichat.modelo;

/**
 * Created by geovan on 06/08/17.
 */
public class Mensagem
{

    private String texto;
    private int id;

    public Mensagem(String texto, int id)
    {
        this.texto = texto;
        this.id = id;
    }

    public String getTexto()
    {
        return texto;
    }

    public int getId()
    {
        return id;
    }
}
