package com.example.geovan.ichat.Callbacks;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContentResolverCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

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
    /*private MainActivity mainActivity;*/

    public OuvirCallback(Context context)
    {
        this.context = context;
    }

    @Override
    public void onResponse(Call<Mensagem> call, Response<Mensagem> response)
    {
        if(response.isSuccessful())
        {
            Mensagem mensagem = response.body();

            Intent nova_mensagem = new Intent("nova_mensagem");
            nova_mensagem.putExtra("mensagem" , mensagem);

            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
            localBroadcastManager.sendBroadcast(nova_mensagem);
        }
    }

    @Override
    public void onFailure(Call<Mensagem> call, Throwable t)
    {
        /*LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
        localBroadcastManager.sendBroadcast(new Intent("ouvir"));*/
    }
}
