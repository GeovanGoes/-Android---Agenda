package com.example.geovan.ichat.Callbacks;

import android.content.Context;

import com.example.geovan.ichat.act.MainActivity;
import com.example.geovan.ichat.modelo.Mensagem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by geovan.goes on 02/10/2017.
 */
public class OuvirCallback implements Callback<Mensagem>
{

    private Context context;
    private MainActivity mainActivity;

    public OuvirCallback(MainActivity mainActivity)
    {
        this.context = context;
    }

    @Override
    public void onResponse(Call<Mensagem> call, Response<Mensagem> response)
    {
        if(response.isSuccessful())
        {
            mainActivity.colocaNaLista(response.body());
            mainActivity.ouvirMensagens();
        }
        else
        {
            mainActivity.ouvirMensagens();
        }
    }

    @Override
    public void onFailure(Call<Mensagem> call, Throwable t)
    {

    }
}
