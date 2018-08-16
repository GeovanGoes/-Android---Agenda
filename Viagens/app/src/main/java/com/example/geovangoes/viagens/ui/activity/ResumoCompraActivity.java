package com.example.geovangoes.viagens.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geovangoes.viagens.R;
import com.example.geovangoes.viagens.model.Pacote;
import com.example.geovangoes.viagens.util.MoedaUtil;
import com.example.geovangoes.viagens.util.ResourcesUtil;

import static com.example.geovangoes.viagens.ui.activity.ActivityConstants.KEY_PACOTE;

public class ResumoCompraActivity extends AppCompatActivity
{

    public static final String APPBAR_TITLE = "Resumo da compra";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_compra);

        setTitle(APPBAR_TITLE);

        Bundle extras = getIntent().getExtras();
        Pacote pacote = extras.getParcelable(KEY_PACOTE);

        if (pacote != null)
            bind(pacote);
    }

    private void bind(Pacote pacote)
    {
        mostrarImagemLocal(pacote);

        mostratLocal(pacote);

        mostrarPeriodo(pacote);

        mostrarPreco(pacote);
    }

    private void mostrarPreco(Pacote pacote)
    {
        TextView preco = findViewById(R.id.resumo_compra_valor);
        preco.setText(MoedaUtil.formatarMoedaPadraoBrasileiro(pacote.getPreco()));
    }

    private void mostrarPeriodo(Pacote pacote)
    {
        TextView periodo = findViewById(R.id.resumo_compra_periodo);
        periodo.setText(ResumoPacoteActivity.getPeriodoDoPacoteFormatado(pacote.getDias()));
    }

    private void mostratLocal(Pacote pacote)
    {
        TextView local = findViewById(R.id.resumo_compra_local);
        local.setText(pacote.getLocal());
    }

    private void mostrarImagemLocal(Pacote pacote)
    {
        ImageView imagemLocal = findViewById(R.id.resumo_compra_imagem_local);
        imagemLocal.setImageDrawable(ResourcesUtil.getDrawable(pacote.getImagem(), this));
    }
}
