package br.com.alura.agenda.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by root on 10/05/17.
 */
public class AlunoConverter {
    public String converterParaJSON(List<Aluno> alunos) {
        JSONStringer jsonStringer = new JSONStringer();
        try {
            jsonStringer.object().key("list").array().object().key("aluno").array();

            for (Aluno aluno:alunos) {
                jsonStringer.object();
                jsonStringer.key("nome").value(aluno.getNome());
                jsonStringer.key("nota").value(aluno.getNota());
                jsonStringer.endObject();
            }
            jsonStringer.endArray();
            jsonStringer.endObject();
            jsonStringer.endArray();
            jsonStringer.endObject();

            return jsonStringer.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
