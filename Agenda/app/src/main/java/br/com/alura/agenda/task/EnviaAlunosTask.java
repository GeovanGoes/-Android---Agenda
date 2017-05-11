package br.com.alura.agenda.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.client.WebClient;
import br.com.alura.agenda.converter.AlunoConverter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by root on 10/05/17.
 */

public class EnviaAlunosTask extends AsyncTask<Void, Void, String> {

    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context,"Aguarde...", "Enviando alunos...", true, true);
    }

    @Override
    protected String doInBackground(Void... params) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.getAlunos();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converterParaJSON(alunos);

        WebClient webClient = new WebClient();
        String post = webClient.post(json);
        return post;
    }

    @Override
    protected void onPostExecute(String o) {
        if (o == null){
            Toast.makeText(context, "deu ruim", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, o, Toast.LENGTH_LONG).show();
        }
        dialog.dismiss();
    }
}
