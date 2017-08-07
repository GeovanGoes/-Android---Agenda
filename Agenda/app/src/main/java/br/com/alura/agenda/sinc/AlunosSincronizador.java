package br.com.alura.agenda.sinc;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.alura.agenda.DTO.AlunoSync;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.event.AtualizaListaAlunoEvent;
import br.com.alura.agenda.modelo.Aluno;
import br.com.alura.agenda.preferences.AlunoPreferences;
import br.com.alura.agenda.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunosSincronizador {
    private final Context context;
    private EventBus eventBus = EventBus.getDefault();
    private AlunoPreferences alunoPreferences;

    public AlunosSincronizador(Context context) {
        this.context = context;
        alunoPreferences = new AlunoPreferences(context);
    }

    public void buscaTodos(){
        if (alunoPreferences.temVersao()){
            buscaNovos();
        } else {
            buscaAlunos();
        }
    }

    private void buscaNovos() {

        String versao = alunoPreferences.getVersao();
        Call<AlunoSync> novos = new RetrofitInicializador().getAlunoService().novos(versao);
        novos.enqueue(buscaAlunosCallback());
    }

    private void buscaAlunos() {
        Call<AlunoSync> lista = new RetrofitInicializador().getAlunoService().lista();
        lista.enqueue(buscaAlunosCallback());
    }

    private Callback<AlunoSync> buscaAlunosCallback() {
        return new Callback<AlunoSync>() {
            @Override
            public void onResponse(Call<AlunoSync> call, Response<AlunoSync> response) {
                AlunoSync alunoSync = response.body();



                sincroniza(alunoSync);

                Log.i("versao",alunoPreferences.getVersao());

                /***
                 * Sincronizaçao com prioridade para o servidor
                 */
                eventBus.post(new AtualizaListaAlunoEvent());
                sincronizaAlunosInternos();
            }

            @Override
            public void onFailure(Call<AlunoSync> call, Throwable t) {
                Log.e("OnFailure:", t.getMessage());
                eventBus.post(new AtualizaListaAlunoEvent());
            }
        };
    }

    public void sincroniza(AlunoSync alunoSync) {
        String momentoDaUltimaModificacao = alunoSync.getMomentoDaUltimaModificacao();

        Log.i("Versao externa", momentoDaUltimaModificacao);
        
        if (temVersaoNova(momentoDaUltimaModificacao))
        {
            alunoPreferences.salvarVersao(momentoDaUltimaModificacao);
            List<Aluno> alunos = alunoSync.getAlunos();
            AlunoDAO dao = new AlunoDAO(context);
            dao.sincroniza(alunos);
            dao.close();
            Log.i("versao atual", alunoPreferences.getVersao());
        }

    }

    private boolean temVersaoNova(String momentoDaUltimaModificacao)
    {
        if(!alunoPreferences.temVersao())
        {
           return true;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

        try
        {
            Date dataExterna = dateFormat.parse(momentoDaUltimaModificacao);
            String versaoInterna = alunoPreferences.getVersao();

            Log.i("Versao Interna", versaoInterna);

            Date dataInterna = dateFormat.parse(versaoInterna);

            return dataExterna.after(dataInterna);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private void sincronizaAlunosInternos(){
        final AlunoDAO dao = new AlunoDAO(context);

        List<Aluno> alunos = dao.listaNaoSincronizados();

        dao.close();

        Call<AlunoSync> atualiza = new RetrofitInicializador().getAlunoService().atualiza(alunos);
        atualiza.enqueue(new Callback<AlunoSync>() {
            @Override
            public void onResponse(Call<AlunoSync> call, Response<AlunoSync> response) {
                AlunoSync body = response.body();
                sincroniza(body);
            }

            @Override
            public void onFailure(Call<AlunoSync> call, Throwable t) {
                dao.close();
            }
        });
    }

    public void deleta(final Aluno aluno) {
        Call<Void> deleta = new RetrofitInicializador().getAlunoService().deleta(aluno.getId());
        deleta.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                new AlunoDAO(context).deletar(aluno);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}