package com.example.geovan.ichat.service;

import com.example.geovan.ichat.modelo.Mensagem;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by geovan.goes on 07/08/2017.
 */

public class RetrofitInicializador
{
    private final Retrofit retrofit;

    public RetrofitInicializador()
    {
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.109:8080/").addConverterFactory(GsonConverterFactory.create()).build();
    }

    public ChatService getChatService()
    {
        return retrofit.create(ChatService.class);
    }
}
