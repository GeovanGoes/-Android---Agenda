package br.comm.a4kontrol.ponto.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.comm.a4kontrol.ponto.modelo.Feriado;
import br.comm.a4kontrol.ponto.modelo.Lancamento;

/**
 * Created by geovan.goes on 08/05/2017.
 */

public class Dao extends SQLiteOpenHelper {

    private static final String TABELA_FERIADOS = "Feriados";
    private static final String TABELA_LANCAMENTOS = "Lancamentos";

    public Dao(Context context) {
        super(context, "Ponto", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = createTabelLancamentos() + createTabelFeriados();
        db.execSQL(sql);
    }

    private String createTabelLancamentos(){
        String createTable = "CREATE TABLE "+
                TABELA_LANCAMENTOS+
                " (" +
                "id INTEGER PRIMARY KEY," +
                "horario TEXT NOT NULL, " +
                "data TEXT NOT NULL);";
        return createTable;
    }

    private String createTabelFeriados(){
        String createTableFeriados = "CREATE TABLE " +
                TABELA_FERIADOS +
                " (" +
                "id INTEGER PRIMARY KEY," +
                "data TEXT NOT NULL);";
        return createTableFeriados;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Lancamento> getLancamentosDoDia(String data){

        String sql = "SELECT * FROM "+TABELA_LANCAMENTOS+" where data = " + data +";";

        if (data == null){
            sql = "SELECT * FROM "+TABELA_LANCAMENTOS+";";
        }

        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        List<Lancamento> lancamentos = new ArrayList<Lancamento>();

        while (cursor.moveToNext()){

            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String horario = cursor.getString(cursor.getColumnIndex("horario"));
            String date = cursor.getString(cursor.getColumnIndex("data"));

            Lancamento lancamento = new Lancamento(id, horario, date);

            lancamentos.add(lancamento);
        }

        cursor.close();
        return lancamentos;
    }

    public List<Feriado> getFeriados(String data){

        String sql = "SELECT * FROM " + TABELA_FERIADOS + " where data = "+data+";";

        if (data == null)
            sql = "SELECT * FROM " + TABELA_FERIADOS + ";";

        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        List<Feriado> feriados = new ArrayList<Feriado>();

        while (cursor.moveToNext()){

            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String date = cursor.getString(cursor.getColumnIndex("data"));

            Feriado feriado = new Feriado(id, date);

            feriados.add(feriado);
        }

        return feriados;
    }

    public void deletar (Feriado feriado){
        String[] params = {String.valueOf(feriado.getId())};
        getWritableDatabase().delete(TABELA_FERIADOS,"id = ?",params);
    }

    public void deletar(Lancamento lancamento){

        String[] params = {String.valueOf(lancamento.getId())};
        getWritableDatabase().delete(TABELA_LANCAMENTOS,"id = ?",params);
    }

    public void atualizar(Lancamento lancamento){

        String[] params = {String.valueOf(lancamento.getId())};
        getWritableDatabase().update(TABELA_LANCAMENTOS, getContentValues(lancamento), "id = ?", params);
    }

    public ContentValues getContentValues(Lancamento lancamento){

        ContentValues dados = new ContentValues();
        dados.put("data", lancamento.getData());
        dados.put("horario", lancamento.getHorario());
        return dados;
    }

    public void inserirFeriado(Feriado feriado){
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABELA_FERIADOS, null, getContentValues(feriado));
    }

    private ContentValues getContentValues(Feriado feriado) {
        ContentValues dados = new ContentValues();
        dados.put("data", feriado.getData());
        return dados;
    }

    public void inserirLancamento(Lancamento lancamento){
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABELA_LANCAMENTOS, null, getContentValues(lancamento));
    }

    public Feriado getFeriado(String data){
        List<Feriado> feriados = getFeriados(data);
        if (feriados != null){
            return feriados.get(0);
        }else{
            return null;
        }
    }

    public boolean ehFeriado(String data) {
        if (getFeriado(data) == null){
            return false;
        }
        return true;
    }
}
