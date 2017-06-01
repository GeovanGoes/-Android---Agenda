package br.com.alura.agenda.sinc;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import br.com.alura.agenda.DTO.AlunoSync;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.event.AtualizaListaAlunoEvent;
import br.com.alura.agenda.modelo.Aluno;
import br.com.alura.agenda.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunosSincronizador {
    private final Context context;
    private EventBus eventBus = EventBus.getDefault();

    public AlunosSincronizador(Context context) {
        this.context = context;
    }

    public void buscaAlunos() {
        Call<AlunoSync> lista = new RetrofitInicializador().getAlunoService().lista();

        lista.enqueue(new Callback<AlunoSync>() {
            @Override
            public void onResponse(Call<AlunoSync> call, Response<AlunoSync> response) {
                AlunoSync alunoSync = response.body();

                List<Aluno> alunos = alunoSync.getAlunos();
                AlunoDAO dao = new AlunoDAO(context);
                dao.sincroniza(alunos);
                dao.close();
                eventBus.post(new AtualizaListaAlunoEvent());
            }

            @Override
            public void onFailure(Call<AlunoSync> call, Throwable t) {
                Log.e("OnFailure:", t.getMessage());
                eventBus.post(new AtualizaListaAlunoEvent());
            }
        });
    }
}