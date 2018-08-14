package com.example.geovangoes.viagens.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import com.example.geovangoes.viagens.R;
import com.example.geovangoes.viagens.dao.PacoteDAO;
import com.example.geovangoes.viagens.model.Pacote;
import com.example.geovangoes.viagens.ui.adapter.ListaPacotesAdapter;

import java.math.BigDecimal;

public class ListaPacotesActivity extends AppCompatActivity
{

    public static final String TITULO_APPBAR = "Pacotes";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pacotes);
        setTitle(TITULO_APPBAR);
        configuraRecycleView();

        Intent intent = new Intent(ListaPacotesActivity.this, ResumoPacoteActivity.class);

        //intent.putExtra("pacote", pacote);
        startActivity(intent);
    }

    private void configuraRecycleView()
    {
        RecyclerView listaPacotesRecyclerView = findViewById(R.id.lista_pacotes_recyclerView);
        listaPacotesRecyclerView.setAdapter(new ListaPacotesAdapter(new PacoteDAO().lista(), this));

    }
}
