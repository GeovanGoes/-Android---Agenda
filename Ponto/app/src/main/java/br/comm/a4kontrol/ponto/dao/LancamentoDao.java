package br.comm.a4kontrol.ponto.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.comm.a4kontrol.ponto.modelo.Lancamento;
import br.comm.a4kontrol.ponto.util.Constants;

/**
 * Created by geovan.goes on 08/05/2017.
 */
public class LancamentoDao extends DAO<Lancamento> {



    LancamentoDao(Context context, DAO dao) {
        super(context, Constants.TABELA_LANCAMENTO, dao);
    }

    @Override
    ContentValues getContentValues(Lancamento lancamento) {
        ContentValues dados = new ContentValues();
        dados.put("data", lancamento.getData());
        dados.put("horario", lancamento.getHorario());
        return dados;
    }

    @Override
    String prepareConsultQuery(String data) {
        String sql = "SELECT * FROM "+getTableName()+" where data = '" + data +"';";

        if (data == null){
            sql = "SELECT * FROM "+getTableName()+";";
        }
        return sql;
    }

    @Override
    List<Lancamento> convertCursorToObject(Cursor cursor) {

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
    String getParamsName() {
        return "data = ?";
    }

    @Override
    String createTableQuery() {
        String createTable = "CREATE TABLE "+
                getTableName()+
                " (" +
                "id INTEGER PRIMARY KEY," +
                "horario TEXT NOT NULL, " +
                "data TEXT NOT NULL);";
        return createTable;
    }

    @Override
    List<String> updateTableQuery() {
        ArrayList<String> sqls = new ArrayList<>();
        sqls.add("DROP TABLE IF EXISTS "+ getTableName());
        return sqls;
    }

    @Override
    String[] getParams(Lancamento item) {
        String[] params = {item.getData()};
        return params;
    }
}
