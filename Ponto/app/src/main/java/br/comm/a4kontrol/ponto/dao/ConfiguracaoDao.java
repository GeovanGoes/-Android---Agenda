package br.comm.a4kontrol.ponto.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.comm.a4kontrol.ponto.modelo.Configuracao;
import br.comm.a4kontrol.ponto.util.Constants;

/**
 * Created by geovan.goes on 12/05/2017.
 */
public class ConfiguracaoDao extends DAO<Configuracao> {

    public ConfiguracaoDao(Context context) {
        super(context, Constants.TABELA_CONFIGURACAO);
    }

    @Override
    public ContentValues getContentValues(Configuracao configuracao) {
        ContentValues dados = new ContentValues();
        dados.put("key", configuracao.getKey());
        dados.put("value", configuracao.getValue());
        return dados;
    }

    @Override
    public String prepareConsultQuery(String param) {
        String sql = "SELECT * FROM " + getTableName() + " where key = '"+param+"';";

        if (param == null)
            sql = "SELECT * FROM " + getTableName() + ";";

        return sql;
    }

    @Override
    public List<Configuracao> convertCursorToObject(Cursor cursor) {

        List<Configuracao> configuracaoList = new ArrayList<Configuracao>();

        while (cursor.moveToNext()){

            String key = cursor.getString(cursor.getColumnIndex("key"));
            String value = cursor.getString(cursor.getColumnIndex("value"));

            Configuracao feriado = new Configuracao(key, value);

            configuracaoList.add(feriado);
        }

        return configuracaoList;
    }

    @Override
    public String getParamsName() {
        return "key = ?";
    }

    @Override
    public String createTableQuery() {
        String createTableFeriados = "CREATE TABLE " +
                getTableName() +
                " (" +
                "key TEXT PRIMARY KEY," +
                "value TEXT NOT NULL);";
        return createTableFeriados;
    }

    @Override
    public String updateTableQuery() {
        return "DROP TABLE IF EXISTS "+ getTableName();
    }
}
