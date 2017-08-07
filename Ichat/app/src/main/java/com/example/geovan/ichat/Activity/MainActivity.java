package com.example.geovan.ichat.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.geovan.ichat.R;
import com.example.geovan.ichat.adapter.MensagemAdapter;
import com.example.geovan.ichat.modelo.Mensagem;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listaDeMensagens = (ListView) findViewById(R.id.lv_mensagens);

        List<Mensagem> mensagens = Arrays.asList(new Mensagem("Opa", 1), new Mensagem("Oi", 2));

        MensagemAdapter adapter = new MensagemAdapter(mensagens,this,1);

        listaDeMensagens.setAdapter(adapter);
    }
}
