package br.com.alura.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by root on 21/04/17.
 */
public class AlunoDAO extends SQLiteOpenHelper{

    public AlunoDAO(Context context)
    {
        super(context, "Agenda", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        String sql = "CREATE TABLE Alunos  " +
                "(" +
                "id INTEGER PRIMARY KEY," +
                "nome TEXT NOT NULL, " +
                "endereco TEXT, " +
                "telefone TEXT, " +
                "site TEXT, " +
                "nota REAL," +
                "caminhoFoto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT";
        db.execSQL(sql);
    }

    public void insereAluno(Aluno aluno) {

        SQLiteDatabase db = getWritableDatabase();
        db.insert("Alunos", null, getContentValues(aluno));
    }

    public List<Aluno> getAlunos(){

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Alunos";
        Cursor resultOfQuery = db.rawQuery(sql, null);

        List<Aluno> alunos = new ArrayList<Aluno>();

        while(resultOfQuery.moveToNext()){

            Long id = resultOfQuery.getLong(resultOfQuery.getColumnIndex("id"));
            String nome = resultOfQuery.getString(resultOfQuery.getColumnIndex("nome"));
            String endereco = resultOfQuery.getString(resultOfQuery.getColumnIndex("endereco"));
            String telefone = resultOfQuery.getString(resultOfQuery.getColumnIndex("telefone"));
            String site = resultOfQuery.getString(resultOfQuery.getColumnIndex("site"));
            float nota = resultOfQuery.getFloat(resultOfQuery.getColumnIndex("nota"));
            String caminhoFoto = resultOfQuery.getString(resultOfQuery.getColumnIndex("caminhoFoto"));

            Aluno aluno = new Aluno(id, nome, endereco, telefone, site, nota, caminhoFoto);
            alunos.add(aluno);
        }
        resultOfQuery.close();
        return alunos;
    }

    public void deletar(Aluno aluno){

        String[] params = {aluno.getId().toString()};
        getWritableDatabase().delete("Alunos","id = ?",params);
    }

    public void atualizar(Aluno aluno){

        String[] params = {aluno.getId().toString()};
        getWritableDatabase().update("Alunos", getContentValues(aluno), "id = ?", params);
    }

    public ContentValues getContentValues(Aluno aluno){

        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getRatting());
        dados.put("caminhoFoto", aluno.getCaminhoFoto());
        return dados;
    }
}
