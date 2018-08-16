package com.example.geovangoes.viagens.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.geovangoes.viagens.R;
import com.example.geovangoes.viagens.dao.PacoteDAO;
import com.example.geovangoes.viagens.model.Pacote;
import com.example.geovangoes.viagens.ui.adapter.ListaPacotesAdapter;
import com.example.geovangoes.viagens.ui.callback.OnItemPacoteClick;

import static com.example.geovangoes.viagens.ui.activity.ActivityConstants.KEY_PACOTE;

public class ListaPacotesActivity extends AppCompatActivity implements OnItemPacoteClick
{

    public static final String TITULO_APPBAR = "Pacotes";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pacotes);
        setTitle(TITULO_APPBAR);
        configuraRecycleView();
    }

    private void configuraRecycleView()
    {
        RecyclerView listaPacotesRecyclerView = findViewById(R.id.lista_pacotes_recyclerView);
        listaPacotesRecyclerView.setAdapter(new ListaPacotesAdapter(new PacoteDAO().lista(), this, this));

    }

    @Override
    public void click(Pacote pacote, int position)
    {
        vaiParaResumoDoPacote(pacote);
    }

    private void vaiParaResumoDoPacote(Pacote pacote)
    {
        Intent intent = new Intent(ListaPacotesActivity.this, ResumoPacoteActivity.class);
        intent.putExtra(KEY_PACOTE, pacote);
        startActivity(intent);
    }
}
