package br.com.alura.agenda.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.adapter.ListaAlunosAdapter;
import br.com.alura.agenda.client.WebClient;
import br.com.alura.agenda.converter.AlunoConverter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;
import br.com.alura.agenda.task.EnviaAlunosTask;

import static br.com.alura.agenda.R.id.lista;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        Button adicionarAluno = (Button) findViewById(R.id.lista_adicionar_aluno);

        adicionarAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToForm = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(goToForm);
            }
        });

        this.listView = (ListView) findViewById(lista);

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {

                Aluno aluno = (Aluno) listView.getItemAtPosition(position);
                Intent goToForm = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                goToForm.putExtra("aluno", aluno);
                startActivity(goToForm);
            }
        });

        registerForContextMenu(this.listView);
    }

    @Override
    protected void onResume() {

        super.onResume();
        carregarLista();
    }

    private void carregarLista() {

        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.getAlunos();

        dao.close();

        ListaAlunosAdapter adapter = new ListaAlunosAdapter(alunos, this);
        this.listView.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        int position = info.position;
        final Aluno aluno = (Aluno) listView.getItemAtPosition(position);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.deletar(aluno);
                dao.close();
                carregarLista();
                Toast.makeText(ListaAlunosActivity.this, aluno.getNome(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        MenuItem visitarSite = menu.add("Visitar site");
        String url = aluno.getSite().startsWith("http://") ? aluno.getSite() : "http://" + aluno.getSite();
        visitarSite.setIntent(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));

        MenuItem sms = menu.add("Enviar SMS");
        sms.setIntent(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("sms:"+aluno.getTelefone())));

        MenuItem mapa = menu.add("Ver no Mapa");
        mapa.setIntent(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("geo:0,0?q="+aluno.getEndereco())));

        MenuItem ligar = menu.add("Ligar");
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String[]{Manifest.permission.CALL_PHONE},123);
                } else {
                    startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+aluno.getTelefone())));
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_enviar_notas:
                EnviaAlunosTask enviaAlunosTask = new EnviaAlunosTask(this);
                enviaAlunosTask.execute();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}