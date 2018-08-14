package com.example.geovangoes.viagens.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geovangoes.viagens.R;
import com.example.geovangoes.viagens.model.Pacote;
import com.example.geovangoes.viagens.util.DiasUtil;
import com.example.geovangoes.viagens.util.ResourcesUtil;

import java.math.BigDecimal;

public class ResumoPacoteActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_pacote);


        Pacote pacote = new Pacote("São Paulo", "sao_paulo_sp", 2, new BigDecimal("243.9"));

        TextView local = findViewById(R.id.resumo_pacote_local);


        ImageView imagem = findViewById(R.id.resumo_pacote_imagem);
        imagem.setImageDrawable(ResourcesUtil.getDrawable(pacote.getImagem(), this));

        TextView dias = findViewById(R.id.resumo_pacote_dias);
        dias.setText(DiasUtil.formataDiasEmTexto(pacote.getDias()));
    }
}
