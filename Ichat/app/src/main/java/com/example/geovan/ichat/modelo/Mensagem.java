package com.example.geovan.ichat.modelo;

import java.io.Serializable;

/**
 * Created by geovan on 06/08/17.
 */
public class Mensagem implements Serializable
{

    private String text;

    private int id;

    public Mensagem(String text, Integer id)
    {
        this.text = text;
        this.id = id;
    }

    public String getText()
    {
        return text;
    }

    public Integer getId()
    {
        return id;
    }
}
