package br.com.alura.agenda.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by geovan.goes on 02/06/2017.
 */

public class AlunoPreferences {

    private static final String ALUNO_PREFERENCES = "br.com.alura.agenda.preferences.AlunoPreferences";
    private static final String VERSAO_DADO = "versao do dado";
    private Context context;

    public AlunoPreferences(Context context) {

        this.context = context;
    }

    public void salvarVersao(String versao){
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(VERSAO_DADO, versao);
        editor.commit();


    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(ALUNO_PREFERENCES, Context.MODE_PRIVATE);
    }

    public String getVersao() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        String versao = sharedPreferences.getString(VERSAO_DADO, "");
        return versao;
    }

    public boolean temVersao() {
        return !getVersao().isEmpty();
    }
}
