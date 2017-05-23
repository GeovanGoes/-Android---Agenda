package br.com.alura.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by root on 21/04/17.
 */
public class AlunoDAO extends SQLiteOpenHelper{

    public AlunoDAO(Context context)
    {
        super(context, "Agenda", null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        String sql = "CREATE TABLE Alunos  " +
                "(" +
                "id CHAR(36) PRIMARY KEY," +
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

/*        Log.d("oldVersion -> ", String.valueOf(oldVersion));
        Log.d("newVersion -> ", String.valueOf(newVersion));

                String criandoNovaTabela = "CREATE TABLE Alunos_novo " +
                        "(id CHAR(36) PRIMARY KEY, " +
                        "nome TEXT NOT NULL, " +
                        "endereco TEXT, " +
                        "telefone TEXT, " +
                        "site TEXT, " +
                        "nota REAL," +
                        "caminhoFoto TEXT);";
                db.execSQL(criandoNovaTabela);

                String inserindoAlunosNaTabelaNova = "INSERT INTO Alunos_novo " +
                        "(id, nome, endereco, telefone, site, nota, caminhoFoto) " +
                        "SELECT id, nome, endereco, telefone, site, nota, caminhoFoto " +
                        "FROM Alunos";
                db.execSQL(inserindoAlunosNaTabelaNova);

                String removendoTabelaAntiga = "DROP TABLE Alunos";
                db.execSQL(removendoTabelaAntiga);

                String alterandoNomeDaTabelNova = "ALTER TABLE Alunos_novo " +
                        "RENAME to Alunos";
                db.execSQL(alterandoNomeDaTabelNova);


                String buscaAlunos = "SELECT * FROM Alunos";
                Cursor resultOfQuery = db.rawQuery(buscaAlunos, null);

                List<Aluno> alunos = getAlunos(resultOfQuery);

                String atualizaIdDoAluno = "UPDATE Alunos " +
                        "SET id=? " +
                        "WHERE id=?";

                for (Aluno aluno : alunos) {
                    db.execSQL(atualizaIdDoAluno, new String[]{gerarUUID(), aluno.getId()});
                }*/
        db.execSQL("DROP TABLE IF EXISTS Alunos");
        onCreate(db);
    }

    private String gerarUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public void insereAluno(Aluno aluno) {
        insereIdSeNecessario(aluno);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("Alunos", null, getContentValues(aluno));
    }

    private void insereIdSeNecessario(Aluno aluno) {
        if (aluno.getId() == null) {
            aluno.setId(gerarUUID());
        }
    }

    public List<Aluno> getAlunos(){

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Alunos";
        Cursor resultOfQuery = db.rawQuery(sql, null);

        List<Aluno> alunos = getAlunos(resultOfQuery);
        resultOfQuery.close();
        return alunos;
    }

    @NonNull
    private List<Aluno> getAlunos(Cursor resultOfQuery) {
        List<Aluno> alunos = new ArrayList<Aluno>();

        while(resultOfQuery.moveToNext()){

            String uuid = resultOfQuery.getString(resultOfQuery.getColumnIndex("id"));
            String nome = resultOfQuery.getString(resultOfQuery.getColumnIndex("nome"));
            String endereco = resultOfQuery.getString(resultOfQuery.getColumnIndex("endereco"));
            String telefone = resultOfQuery.getString(resultOfQuery.getColumnIndex("telefone"));
            String site = resultOfQuery.getString(resultOfQuery.getColumnIndex("site"));
            Double nota = resultOfQuery.getDouble(resultOfQuery.getColumnIndex("nota"));
            String caminhoFoto = resultOfQuery.getString(resultOfQuery.getColumnIndex("caminhoFoto"));

            Aluno aluno = new Aluno(uuid, nome, endereco, telefone, site, nota, caminhoFoto);
            alunos.add(aluno);
        }
        return alunos;
    }

    public void deletar(Aluno aluno){

        String[] params = {aluno.getId()};
        getWritableDatabase().delete("Alunos","id = ?",params);
    }

    public void atualizar(Aluno aluno){

        String[] params = {aluno.getId()};
        getWritableDatabase().update("Alunos", getContentValues(aluno), "id = ?", params);
    }

    public ContentValues getContentValues(Aluno aluno){

        ContentValues dados = new ContentValues();
        dados.put("id", aluno.getId());
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        dados.put("caminhoFoto", aluno.getCaminhoFoto());
        return dados;
    }

    public boolean ehAluno(String telefone){

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM Alunos where telefone = ?", new String[]{telefone});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public void sincroniza(List<Aluno> listaAlunos) {
        for (Aluno aluno : listaAlunos){
            if (existe(aluno)){
                atualizar(aluno);
            } else {
                insereAluno(aluno);
            }
        }
    }

    private boolean existe(Aluno aluno) {

        SQLiteDatabase db = getReadableDatabase();

        String existe = "SELECT id FROM Alunos WHERE id = ? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{aluno.getId()});
        int count = cursor.getCount();
        if(count > 0){
            return true;
        }
        return false;
    }
}
