package br.com.alura.agenda.retrofit;

import java.util.logging.Level;

import br.com.alura.agenda.services.AlunoService;
import br.com.alura.agenda.services.DispositivoService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by geovan.goes on 22/05/2017.
 */

public class RetrofitInicializador {

    private final Retrofit reftrofit;

    public RetrofitInicializador() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);

        reftrofit = new Retrofit.Builder().baseUrl("http://192.168.15.8:8080/api/").addConverterFactory(JacksonConverterFactory.create()).client(client.build()).build();
    }

    public AlunoService getAlunoService() {
        return reftrofit.create(AlunoService.class);
    }

    public DispositivoService getDispositivoService() {
        return reftrofit.create(DispositivoService.class);
    }
}
