package br.comm.a4kontrol.ponto.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.comm.a4kontrol.ponto.helper.LogHelper;
import br.comm.a4kontrol.ponto.util.Constants;

/**
 * Created by geovan.goes on 09/05/2017.
 */

public abstract class DAO<T> extends SQLiteOpenHelper implements AbstractDAO<T>{

    private String TABLE_NAME;

    public DAO(Context context, String tableName) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        TABLE_NAME = tableName;
    }

    @Override
    public void insere(T item) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.insert(getTableName(), null, getContentValues(item));
        } catch(Exception e) {
            LogHelper.error(this, e, "");
        }
    }

    @Override
    public List<T> lista(String param) {

        List<T> result = new ArrayList<T>();

        try{
            String sql = prepareConsultQuery(param);
            Cursor cursor = getReadableDatabase().rawQuery(sql, null);
            result = convertCursorToObject(cursor);
        }catch(Exception e){
            LogHelper.error(this, e, "");
        }

        return result;
    }

    @Override
    public void deleta(String[] params) {
        try {
            getWritableDatabase().delete(getTableName(), getParamsName(), params);
        } catch (Exception e) {
            LogHelper.error(this, e, "");
        }
    }

    @Override
    public void atualiza(String[] params) {
        // TODO: Implementar caso seja necessário
    }

    protected String getTableName(){
        return this.TABLE_NAME;
    }

    public abstract ContentValues getContentValues(T object);

    public abstract String prepareConsultQuery(String param);

    public abstract List<T> convertCursorToObject(Cursor cursor);

    public abstract String getParamsName();
}
