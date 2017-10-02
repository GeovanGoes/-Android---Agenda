package com.example.geovan.ichat.service;

import com.example.geovan.ichat.modelo.Mensagem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by geovan.goes on 07/08/2017.
 */
public interface ChatService
{
    @POST("polling")
    Call<Void> enviar(@Body  Mensagem mensagem);

    @GET("polling")
    Call<Mensagem> ouvirMensagens();
}
