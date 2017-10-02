package com.example.geovan.ichat.modelo;

/**
 * Created by geovan on 06/08/17.
 */
public class Mensagem
{

    private String text;
    private Integer id;

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
