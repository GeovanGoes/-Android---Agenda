package br.comm.a4kontrol.ponto.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.comm.a4kontrol.ponto.helper.LogHelper;
import br.comm.a4kontrol.ponto.modelo.Lancamento;

/**
 * Created by geovan.goes on 08/05/2017.
 */
public class LancamentoDao extends DAO<Lancamento> {

    private static final String TABELA_LANCAMENTOS = "Lancamentos";

    public LancamentoDao(Context context) {
        super(context);
    }

    @Override
    public ContentValues getContentValues(Lancamento lancamento) {
        ContentValues dados = new ContentValues();
        dados.put("data", lancamento.getData());
        dados.put("horario", lancamento.getHorario());
        return dados;
    }

    @Override
    public String prepareConsultQuery(String data) {
        String sql = "SELECT * FROM "+TABELA_LANCAMENTOS+" where data = '" + data +"';";

        if (data == null){
            sql = "SELECT * FROM "+TABELA_LANCAMENTOS+";";
        }
        return sql;
    }

    @Override
    public List<Lancamento> convertCursorToObject(Cursor cursor) {

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

    @Override
    public String getTableName() {
        return TABELA_LANCAMENTOS;
    }

    @Override
    public String getParamsName() {
        return "id = ?";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(createTabelLancamentos());
        }catch (Exception e){
            LogHelper.error(this, e, null);
        }
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS "+ TABELA_LANCAMENTOS;
        db.execSQL(sql);
        onCreate(db);
    }
}
