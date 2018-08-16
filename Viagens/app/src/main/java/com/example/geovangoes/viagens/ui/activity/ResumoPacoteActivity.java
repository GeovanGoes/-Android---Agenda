package com.example.geovangoes.viagens.ui.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geovangoes.viagens.R;
import com.example.geovangoes.viagens.model.Pacote;
import com.example.geovangoes.viagens.util.DiasUtil;
import com.example.geovangoes.viagens.util.MoedaUtil;
import com.example.geovangoes.viagens.util.ResourcesUtil;
import static com.example.geovangoes.viagens.ui.activity.ActivityConstants.KEY_PACOTE;
import java.util.Calendar;

public class ResumoPacoteActivity extends AppCompatActivity
{

    public static final String TITULO_APPBAR = "Resumo do pacote";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_pacote);

        setTitle(TITULO_APPBAR);

        Bundle extras = getIntent().getExtras();
        Pacote pacote = extras.getParcelable(KEY_PACOTE);

        if (pacote != null)
            bind(pacote);
    }

    /***
     *
     * @param pacote
     */
    private void bind(Pacote pacote)
    {
        mostraLocal(pacote);
        mostraImagem(pacote);
        mostraDias(pacote);
        mostraData(pacote);
        mostraPreco(pacote);
        configurarBotaoRealizarPagamento(pacote);
    }

    private void configurarBotaoRealizarPagamento(Pacote pacote)
    {
        findViewById(R.id.resumo_pacote_botao_realiza_pagamento).setOnClickListener((view) -> vaiParaPagamento(pacote));
    }

    private void vaiParaPagamento(Pacote pacote)
    {
        Intent irParaPagamento = new Intent(ResumoPacoteActivity.this, PagamentoActivity.class);
        irParaPagamento.putExtra(KEY_PACOTE, pacote);
        startActivity(irParaPagamento);
    }

    private void mostraPreco(Pacote pacote)
    {
        TextView preco = findViewById(R.id.resumo_pacote_preco);
        preco.setText(MoedaUtil.formatarMoedaPadraoBrasileiro(pacote.getPreco()));
    }

    private void mostraData(Pacote pacote)
    {
        TextView data = findViewById(R.id.resumo_pacote_data);
        data.setText(getPeriodoDoPacoteFormatado(pacote.getDias()));
    }

    private void mostraDias(Pacote pacote)
    {
        TextView dias = findViewById(R.id.resumo_pacote_dias);
        dias.setText(DiasUtil.formataDiasEmTexto(pacote.getDias()));
    }

    private void mostraImagem(Pacote pacote)
    {
        ImageView imagem = findViewById(R.id.resumo_pacote_imagem);
        imagem.setImageDrawable(ResourcesUtil.getDrawable(pacote.getImagem(), this));
    }

    private void mostraLocal(Pacote pacote)
    {
        TextView local = findViewById(R.id.resumo_pacote_local);
        local.setText(pacote.getLocal());
    }

    @NonNull
    public static String getPeriodoDoPacoteFormatado(int dias)
    {
        Calendar calHoje = Calendar.getInstance();
        String hoje = DiasUtil.formatarData(calHoje);
        Calendar hojeMaisDias = DiasUtil.somaDias(dias, calHoje);
        String dataFim = DiasUtil.formatarData(hojeMaisDias);
        return hoje + " - " + dataFim + " de " + hojeMaisDias.get(Calendar.YEAR);
    }
}
