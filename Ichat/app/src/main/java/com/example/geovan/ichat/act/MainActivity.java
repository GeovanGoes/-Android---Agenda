package com.example.geovan.ichat.act;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.geovan.ichat.Callbacks.FalarCallback;
import com.example.geovan.ichat.Callbacks.OuvirCallback;
import com.example.geovan.ichat.R;
import com.example.geovan.ichat.adapter.MensagemAdapter;
import com.example.geovan.ichat.app.ChatApplication;
import com.example.geovan.ichat.component.ChatComponent;
import com.example.geovan.ichat.modelo.Mensagem;
import com.example.geovan.ichat.service.ChatService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.campo_mensagem)
    EditText campoMensagem;

    @BindView(R.id.botao_enviar)
    Button botaoEnviar;

    @BindView(R.id.lv_mensagens)
    ListView listaDeMensagens;

    @BindView(R.id.iv_avatar_usuario)
    ImageView avatarUsuario;

    private int idDoCliente = 10;
    private List<Mensagem> mensagens;

    @Inject
    ChatService chatService;

    private ChatComponent component;

    @Inject
    Picasso picasso;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Mensagem mensagem = (Mensagem) intent.getSerializableExtra("mensagem");
            colocaNaLista(mensagem);
        }
    };

    /*private BroadcastReceiver receiverOuvir = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ouvirMensagens();
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        picasso.with(this).load("http://api.adorable.io/avatars/285/" + idDoCliente + ".png").into(avatarUsuario);

        ChatApplication app = (ChatApplication) getApplication();
        component = app.getComponent();
        component.inject(this);

        mensagens = new ArrayList<>();

        ouvirMensagens();

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(receiver, new IntentFilter("nova_mensagem"));
        //localBroadcastManager.registerReceiver(receiverOuvir, new IntentFilter("ouvir"));
    }

    @OnClick(R.id.botao_enviar)
    public void enviarMensagem() {
        Call enviarCall = chatService.enviar(new Mensagem(campoMensagem.getText().toString(), idDoCliente));
        enviarCall.enqueue(new FalarCallback(MainActivity.this));
    }

    public void ouvirMensagens() {
        Call<Mensagem> mensagemCall = chatService.ouvirMensagens();
        mensagemCall.enqueue(new OuvirCallback(this));
    }

    public void colocaNaLista(Mensagem mensagem) {
        if (mensagem != null) {
            mensagens.add(mensagem);
            MensagemAdapter adapter = new MensagemAdapter(mensagens, this, idDoCliente);

            listaDeMensagens.setAdapter(adapter);
        }
        ouvirMensagens();
    }

    @Override
    protected void onStop() {
        super.onStop();

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.unregisterReceiver(receiver);
        //localBroadcastManager.unregisterReceiver(receiverOuvir);
    }
}
