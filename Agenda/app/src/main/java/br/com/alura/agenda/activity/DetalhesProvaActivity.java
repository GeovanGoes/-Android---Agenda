package br.com.alura.agenda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.alura.agenda.R;
import br.com.alura.agenda.modelo.Prova;

public class DetalhesProvaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_prova);

        Intent intent = getIntent();
        Prova prova = (Prova) intent.getSerializableExtra("Prova");


        TextView materia = (TextView) findViewById(R.id.detalhes_prova_materia);
        materia.setText(prova.getMateria());

        TextView data = (TextView) findViewById(R.id.detalhes_prova_data);
        data.setText(prova.getData());

        ListView topicos = (ListView) findViewById(R.id.detalhes_prova_topicos);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prova.getTopicos());
        topicos.setAdapter(adapter);
    }
}
