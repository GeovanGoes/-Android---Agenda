package br.comm.a4kontrol.ponto.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.comm.a4kontrol.ponto.modelo.Feriado;
import br.comm.a4kontrol.ponto.util.Constants;

/**
 * Created by geovan.goes on 09/05/2017.
 */
public class FeriadoDao extends DAO<Feriado> {

    FeriadoDao(Context context, DAO dao) {
        super(context, Constants.TABELA_FERIADO, dao);
    }

    @Override
    ContentValues getContentValues(Feriado feriado) {
        ContentValues dados = new ContentValues();
        dados.put("data", feriado.getData());
        return dados;
    }

    @Override
    String prepareConsultQuery(String data) {
        String sql = "SELECT * FROM " + getTableName() + " where data = '"+data+"';";

        if (data == null)
            sql = "SELECT * FROM " + getTableName() + ";";

        return sql;
    }

    @Override
    List<Feriado> convertCursorToObject(Cursor cursor) {
        List<Feriado> feriados = new ArrayList<Feriado>();

        while (cursor.moveToNext()){

            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String date = cursor.getString(cursor.getColumnIndex("data"));

            Feriado feriado = new Feriado(id, date);

            feriados.add(feriado);
        }

        return feriados;
    }

    @Override
    String getParamsName() {
        return "data = ?";
    }

    @Override
    String createTableQuery() {
        String createTableFeriados = "CREATE TABLE " +
                getTableName() +
                " (" +
                "id INTEGER PRIMARY KEY," +
                "data TEXT NOT NULL);";
        return createTableFeriados;
    }

    @Override
    List<String> updateTableQuery() {
        ArrayList<String> sqls = new ArrayList<>();
        sqls.add("DROP TABLE IF EXISTS "+ getTableName());
        return sqls;
    }

    @Override
    String[] getParams(Feriado item) {
        String[] params = {item.getData()};
        return params;
    }
}