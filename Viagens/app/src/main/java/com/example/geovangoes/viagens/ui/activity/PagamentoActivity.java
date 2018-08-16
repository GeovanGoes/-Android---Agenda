package com.example.geovangoes.viagens.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.geovangoes.viagens.R;
import com.example.geovangoes.viagens.model.Pacote;
import com.example.geovangoes.viagens.util.MoedaUtil;
import static com.example.geovangoes.viagens.ui.activity.ActivityConstants.KEY_PACOTE;

public class PagamentoActivity extends AppCompatActivity
{

    public static final String APPBAR_TITLE = "Pagamento";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        setTitle(APPBAR_TITLE);

        Bundle extras = getIntent().getExtras();
        Pacote pacote = extras.getParcelable(KEY_PACOTE);

        if (pacote != null)
            bind(pacote);
    }

    private void bind(Pacote pacote)
    {
        mostrarPreco(pacote);
        configurarBotaoFinalizarCompra(pacote);
    }

    private void configurarBotaoFinalizarCompra(Pacote pacote)
    {
        Button finalizarCompra = findViewById(R.id.pagamento_finalizar_compra);

        finalizarCompra.setOnClickListener((view) -> vaiParaResumoCompra(pacote));
    }

    private void vaiParaResumoCompra(Pacote pacote)
    {
        Intent irParaTelaDeResumo = new Intent(PagamentoActivity.this, ResumoCompraActivity.class);
        irParaTelaDeResumo.putExtra(KEY_PACOTE, pacote);
        startActivity(irParaTelaDeResumo);
    }

    private void mostrarPreco(Pacote pacote)
    {
        TextView preco = findViewById(R.id.pagamento_preco_pacote);
        preco.setText(MoedaUtil.formatarMoedaPadraoBrasileiro(pacote.getPreco()));
    }
}
