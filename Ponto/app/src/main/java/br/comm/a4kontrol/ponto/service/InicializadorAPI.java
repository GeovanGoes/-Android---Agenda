package br.comm.a4kontrol.ponto.service;

import android.util.Base64;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by geovan.goes
 */

public class InicializadorAPI
{

    Retrofit retrofit;

    public InicializadorAPI()
    {
        String baseURL = "https://ponto-service.herokuapp.com/";

        //Just for dev
        String authToken = Credentials.basic("Geovan", "123");

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(chain -> {
            Response response = chain.proceed(chain.request());

            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("Authorization", authToken)
                    .header("Accept", "application/json; charset=utf-8")
                    .method(original.method(), original.body())
                    .build();

            response = chain.proceed(request);

            return response;
        });

        retrofit = new Retrofit.
                Builder().
                baseUrl(baseURL).
                addConverterFactory(GsonConverterFactory.create())
                .client(client.build()).
                build();
    }

    public PontoService getPontoService()
    {
        return retrofit.create(PontoService.class);
    }
}
