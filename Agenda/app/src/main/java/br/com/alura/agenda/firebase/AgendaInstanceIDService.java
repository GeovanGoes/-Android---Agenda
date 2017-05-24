package br.com.alura.agenda.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import br.com.alura.agenda.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by geovan.goes on 24/05/2017.
 */

public class AgendaInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Token Firebase: ", "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        enviaTokenParaServidor(refreshedToken);
    }

    private void enviaTokenParaServidor(final String refreshedToken) {

        Call<Void> enviaToken = new RetrofitInicializador().getDispositivoService().enviaToken(refreshedToken);

        enviaToken.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("OnResponse Token", refreshedToken);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("onFailure Token", t.getMessage());
            }
        });


    }
}
