package com.example.geovan.ichat.Callbacks;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.geovan.ichat.act.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by geovan.goes on 02/10/2017.
 */

public class FalarCallback implements Callback
{
    private Context context;

    public FalarCallback(Context context)
    {
        this.context = context;
    }

    @Override
    public void onResponse(Call call, Response response)
    {
       
    }

    @Override
    public void onFailure(Call call, Throwable t)
    {
        /*Log.i("FALHA", "Não Enviado...");
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
        localBroadcastManager.sendBroadcast(new Intent("ouvir"));*/
    }
}
