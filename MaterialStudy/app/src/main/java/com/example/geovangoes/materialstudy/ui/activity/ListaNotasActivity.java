package com.example.geovangoes.materialstudy.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geovangoes.materialstudy.R;
import com.example.geovangoes.materialstudy.dao.NotaDAO;
import com.example.geovangoes.materialstudy.expandable.MainActivity;
import com.example.geovangoes.materialstudy.model.Nota;
import com.example.geovangoes.materialstudy.ui.recycler.adapter.ListaNotasAdapter;
import com.example.geovangoes.materialstudy.ui.recycler.helper.callback.NotaItemTouchHelperCallback;

import java.util.List;

import static com.example.geovangoes.materialstudy.ui.activity.NotaActivityConstants.CHAVE_NOTA;
import static com.example.geovangoes.materialstudy.ui.activity.NotaActivityConstants.CHAVE_POSICAO;
import static com.example.geovangoes.materialstudy.ui.activity.NotaActivityConstants.CODIGO_REQUISICAO_ALTERA_NOTA;
import static com.example.geovangoes.materialstudy.ui.activity.NotaActivityConstants.CODIGO_REQUISICAO_INSERE_NOTA;
import static com.example.geovangoes.materialstudy.ui.activity.NotaActivityConstants.POSICAO_INVALIDA;

public class ListaNotasActivity extends AppCompatActivity
{
    public static final String TITULO_APPBAR = "Notas";
    private ListaNotasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        TextView botao = findViewById(R.id.lista_notas_insere_nota);
        configuraBotaoInsereNota(botao);

        configuraRecyclerView(pegaTodasNotas());

        setTitle(TITULO_APPBAR);
    }

    private List<Nota> pegaTodasNotas()
    {
        NotaDAO notaDAO = new NotaDAO();
        for (int i = 1 ; i < 10 ; i++)
            notaDAO.insere(new Nota("titulo: " + i , "descricao: " + i));
        return notaDAO.todos();
    }

    private void configuraBotaoInsereNota(TextView botao)
    {
        botao.setOnClickListener((view) -> vaiParaFormularioActivityInsere());
    }

    private void vaiParaFormularioActivityInsere()
    {
        startActivityForResult(new Intent(ListaNotasActivity.this, FormularioNotaActivity.class), CODIGO_REQUISICAO_INSERE_NOTA);
    }

    private void configuraRecyclerView(List<Nota> todos)
    {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todos, listaNotas);
        configuraItemTouchHelper(listaNotas);
    }

    private void configuraItemTouchHelper(RecyclerView listaNotas)
    {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }

    private void configuraAdapter(List<Nota> todos, RecyclerView listaNotas)
    {
        adapter = new ListaNotasAdapter(this, todos);
        adapter.setOnItemClickListener((nota, posicao) -> vaiParaFormularioNotaActivityAltera(nota, posicao));
        listaNotas.setAdapter(adapter);
    }

    private void vaiParaFormularioNotaActivityAltera(Nota nota, int posicao)
    {
        Intent abreFormularioComNota = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        abreFormularioComNota.putExtra(CHAVE_NOTA, nota);
        abreFormularioComNota.putExtra(CHAVE_POSICAO, posicao);
        startActivityForResult(abreFormularioComNota, CODIGO_REQUISICAO_ALTERA_NOTA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (ehUmResultadoInsereNota(requestCode, data))
        {
            if (resultadoOk(resultCode))
                adiciona(data);
        }
        else if (ehResultadoAlteraNota(requestCode, data))
        {
            if (resultadoOk(resultCode))
            {
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                int posicaoRecebida = data.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
                if (ehPosicaoValida(posicaoRecebida))
                {
                    altera(notaRecebida, posicaoRecebida);
                }
                else
                {
                    Toast.makeText(this, "Ocorreu um problema na alteração da nota.", Toast.LENGTH_SHORT).show();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void altera(Nota nota, int posicao)
    {
        new NotaDAO().altera(posicao, nota);
        adapter.altera(posicao, nota);
    }

    private boolean ehPosicaoValida(int posicaoRecebida)
    {
        return posicaoRecebida > POSICAO_INVALIDA;
    }

    private boolean ehResultadoAlteraNota(int requestCode, Intent data)
    {
        return ehCodigoRequisicaoAlteraNota(requestCode) && temNota(data);
    }
    private boolean ehCodigoRequisicaoAlteraNota(int requestCode)
    {
        return requestCode == CODIGO_REQUISICAO_ALTERA_NOTA;
    }

    private void adiciona(Intent data)
    {
        Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
        new NotaDAO().insere(notaRecebida);
        adapter.adiciona(notaRecebida);
    }

    private boolean ehUmResultadoInsereNota(int requestCode, Intent data)
    {
        return ehCodigoRequisicaoInsereNota(requestCode) &&
                temNota(data);
    }

    private boolean temNota(Intent data)
    {
        return data != null && data.hasExtra(CHAVE_NOTA);
    }

    private boolean resultadoOk(int resultCode)
    {
        return resultCode == Activity.RESULT_OK;
    }

    private boolean ehCodigoRequisicaoInsereNota(int requestCode)
    {
        return requestCode == CODIGO_REQUISICAO_INSERE_NOTA;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_expandable_recycleview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        startActivity(new Intent(ListaNotasActivity.this, MainActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
