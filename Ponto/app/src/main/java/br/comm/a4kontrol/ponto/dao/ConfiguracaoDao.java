package br.comm.a4kontrol.ponto.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.comm.a4kontrol.ponto.helper.LogHelper;
import br.comm.a4kontrol.ponto.modelo.Configuracao;

/**
 * Created by geovan.goes on 12/05/2017.
 */
public class ConfiguracaoDao extends DAO<Configuracao> {

    public ConfiguracaoDao(Context context) {
        super(context, "Configuracoes");
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
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(createTabelConfiguracoes());
        }catch (Exception e){
            LogHelper.error(this, e, null);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS "+ getTableName();
        db.execSQL(sql);
        onCreate(db);
    }

    private String createTabelConfiguracoes(){
        String createTableFeriados = "CREATE TABLE " +
                getTableName() +
                " (" +
                "key TEXT PRIMARY KEY," +
                "value TEXT NOT NULL);";
        return createTableFeriados;
    }
}
