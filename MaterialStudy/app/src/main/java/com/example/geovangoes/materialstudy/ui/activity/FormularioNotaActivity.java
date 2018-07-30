package com.example.geovangoes.materialstudy.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.geovangoes.materialstudy.R;
import com.example.geovangoes.materialstudy.model.Nota;

import static com.example.geovangoes.materialstudy.ui.activity.NotaActivityConstants.CHAVE_NOTA;
import static com.example.geovangoes.materialstudy.ui.activity.NotaActivityConstants.CHAVE_POSICAO;
import static com.example.geovangoes.materialstudy.ui.activity.NotaActivityConstants.POSICAO_INVALIDA;

public class FormularioNotaActivity extends AppCompatActivity
{

    public static final String TITULO_APPBAR_INSERE = "Insere Nota";
    public static final String TITULO_APPBAR_ALTERA = "Altera Nota";
    private int posicaoRecebida = POSICAO_INVALIDA;
    private TextView titulo;
    private TextView descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        setTitle(TITULO_APPBAR_INSERE);

        incializaCampos();

        Intent dadosRecebidos = getIntent();
        if (dadosRecebidos.hasExtra(CHAVE_NOTA))
        {
            setTitle(TITULO_APPBAR_ALTERA);
            Nota notaRecebida = (Nota) dadosRecebidos.getSerializableExtra(CHAVE_NOTA);
            posicaoRecebida = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);

            preencheCampos(notaRecebida);
        }
    }

    private void preencheCampos(Nota notaRecebida)
    {
        titulo.setText(notaRecebida.getTitulo());
        descricao.setText(notaRecebida.getDescricao());
    }

    private void incializaCampos()
    {
        titulo = findViewById(R.id.formulario_nota_titulo);
        descricao = findViewById(R.id.formulario_nota_descricao);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_formulario_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (ehMenuSalvaNota(item))
        {
            Nota novaNota = criaNota();
            retornaNota(novaNota);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void retornaNota(Nota novaNota)
    {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_NOTA, novaNota);
        resultadoInsercao.putExtra(CHAVE_POSICAO, posicaoRecebida);
        setResult(Activity.RESULT_OK, resultadoInsercao);
    }

    @NonNull
    private Nota criaNota()
    {
        return new Nota(titulo.getText().toString(), descricao.getText().toString());
    }

    private boolean ehMenuSalvaNota(MenuItem item)
    {
        return item.getItemId() == R.id.menu_formulario_nota_ic_salva;
    }
}
