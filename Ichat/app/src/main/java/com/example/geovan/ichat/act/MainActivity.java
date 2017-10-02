package com.example.geovan.ichat.act;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.geovan.ichat.Callbacks.OuvirCallback;
import com.example.geovan.ichat.R;
import com.example.geovan.ichat.adapter.MensagemAdapter;
import com.example.geovan.ichat.modelo.Mensagem;
import com.example.geovan.ichat.service.ChatService;
import com.example.geovan.ichat.service.RetrofitInicializador;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText campoMensagem;
    private int idDoCliente = 1;
    private Button botaoEnviar;
    private List<Mensagem> mensagens;
    private ListView listaDeMensagens;
    private ChatService chatService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        listaDeMensagens = (ListView) findViewById(R.id.lv_mensagens);

        mensagens = new ArrayList<Mensagem>();

        colocaNaLista(new Mensagem("Teste",1));

        botaoEnviar = (Button) findViewById(R.id.botao_enviar);
        campoMensagem = (EditText) findViewById(R.id.campo_mensagem);

        chatService = new RetrofitInicializador().getChatService();

        ouvirMensagens();

        botaoEnviar.setOnClickListener((View v) ->
        {
            Call enviarCall = chatService.enviar(new Mensagem(campoMensagem.getText().toString(), idDoCliente));

            enviarCall.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response)
                {
                    Log.i("SUCESSO", "Enviado...");
                    ouvirMensagens();
                }

                @Override
                public void onFailure(Call call, Throwable t)
                {
                    Log.i("FALHA", "Não Enviado...");
                    ouvirMensagens();
                }
            });
        });
    }

    public void ouvirMensagens()
    {
        Call<Mensagem> mensagemCall = chatService.ouvirMensagens();
        mensagemCall.enqueue(new OuvirCallback(this));
    }

    public void colocaNaLista(Mensagem mensagem)
    {
        mensagens.add(mensagem);
        MensagemAdapter adapter = new MensagemAdapter(mensagens,this,1);
        listaDeMensagens.setAdapter(adapter);
    }
}
