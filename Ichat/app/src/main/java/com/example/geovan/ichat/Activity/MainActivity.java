package com.example.geovan.ichat.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.geovan.ichat.R;
import com.example.geovan.ichat.adapter.MensagemAdapter;
import com.example.geovan.ichat.modelo.Mensagem;
import com.example.geovan.ichat.service.ChatService;
import com.example.geovan.ichat.service.RetrofitInicializador;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText campoMensagem;
    private int idDoCliente = 1;
    private Button botaoEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listaDeMensagens = (ListView) findViewById(R.id.lv_mensagens);

        List<Mensagem> mensagens = Arrays.asList(new Mensagem("Opa", 1), new Mensagem("Me chupa!", 2));

        MensagemAdapter adapter = new MensagemAdapter(mensagens,this,1);

        listaDeMensagens.setAdapter(adapter);

        botaoEnviar = (Button) findViewById(R.id.botao_enviar);
        campoMensagem = (EditText) findViewById(R.id.campo_mensagem);

        Call<Mensagem> mensagemCall = new RetrofitInicializador().getChatService().ouvirMensagens();

        mensagemCall.enqueue(new Callback<Mensagem>()
        {
            @Override
            public void onResponse(Call<Mensagem> call, Response<Mensagem> response)
            {
                Log.i("Sucesso", "onResponse: "+response.body());
            }

            @Override
            public void onFailure(Call<Mensagem> call, Throwable t)
            {

            }
        });

        botaoEnviar.setOnClickListener((View v) ->
            {
                //new ChatService().enviar(new Mensagem(campoMensagem.getText().toString(),idDoCliente));
            }
        );
    }
}
