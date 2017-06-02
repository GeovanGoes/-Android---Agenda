package br.com.alura.agenda.sinc;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

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

    @NonNull
    private Callback<AlunoSync> buscaAlunosCallback() {
        return new Callback<AlunoSync>() {
            @Override
            public void onResponse(Call<AlunoSync> call, Response<AlunoSync> response) {
                AlunoSync alunoSync = response.body();

                String momentoDaUltimaModificacao = alunoSync.getMomentoDaUltimaModificacao();
                alunoPreferences.salvarVersao(momentoDaUltimaModificacao);
                List<Aluno> alunos = alunoSync.getAlunos();
                AlunoDAO dao = new AlunoDAO(context);
                dao.sincroniza(alunos);
                dao.close();

                Log.i("versao",alunoPreferences.getVersao());

                eventBus.post(new AtualizaListaAlunoEvent());
            }

            @Override
            public void onFailure(Call<AlunoSync> call, Throwable t) {
                Log.e("OnFailure:", t.getMessage());
                eventBus.post(new AtualizaListaAlunoEvent());
            }
        };
    }
}