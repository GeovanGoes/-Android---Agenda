package br.com.alura.agenda.services;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by geovan.goes on 24/05/2017.
 */
public interface DispositivoService {

    @POST("firebase/dispositivo/{token}")
    Call<Void> enviaToken(@Header("token") String token);
}
