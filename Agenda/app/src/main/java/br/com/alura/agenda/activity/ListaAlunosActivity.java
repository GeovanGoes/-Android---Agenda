package br.com.alura.agenda.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.adapter.ListaAlunosAdapter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.event.AtualizaListaAlunoEvent;
import br.com.alura.agenda.modelo.Aluno;
import br.com.alura.agenda.retrofit.RetrofitInicializador;
import br.com.alura.agenda.sinc.AlunosSincronizador;
import br.com.alura.agenda.task.EnviaAlunosTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaAlunosActivity extends AppCompatActivity {

    private final AlunosSincronizador alunosSincronizador = new AlunosSincronizador(this);
    private ListView listView;
    private SwipeRefreshLayout swype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);


        swype = (SwipeRefreshLayout) findViewById(R.id.swipe_lista_aluno);
        swype.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                alunosSincronizador.buscaTodos();
            }
        });


        Button adicionarAluno = (Button) findViewById(R.id.lista_adicionar_aluno);
        adicionarAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToForm = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(goToForm);
            }
        });

        this.listView = (ListView) findViewById(R.id.lista);

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

        alunosSincronizador.buscaTodos();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void atualizaListaAlunoEvent(AtualizaListaAlunoEvent atualizaListaAlunoEvent){

        if(swype.isRefreshing()){
            swype.setRefreshing(false);
        }
        carregarLista();
    }

    @Override
    protected void onResume() {

        super.onResume();

        EventBus eventBus = EventBus.getDefault();
        eventBus.register(this);

        carregarLista();
    }

    private void carregarLista() {

        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.getAlunos();

        for (Aluno aluno : alunos) {
            Log.d("Id do"+aluno.getNome()+":",aluno.getId() == null ? "" : aluno.getId());
        }

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

                Call<Void> deleta = new RetrofitInicializador().getAlunoService().deleta(aluno.getId());
                deleta.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                        dao.deletar(aluno);
                        dao.close();
                        carregarLista();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ListaAlunosActivity.this, "Nao foi possivel remover o aluno.", Toast.LENGTH_SHORT).show();
                    }
                });


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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_enviar_notas:
                EnviaAlunosTask enviaAlunosTask = new EnviaAlunosTask(this);
                enviaAlunosTask.execute();
                break;

            case R.id.menu_baixar_provas:
                Intent intent = new Intent(this, ProvasActivity.class);
                startActivity(intent);
                break;

            case R.id.menu_mapa:
                Intent mapa = new Intent(this, MapsActivity.class);
                startActivity(mapa);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus eventBus = EventBus.getDefault();
        eventBus.unregister(this);
    }
}