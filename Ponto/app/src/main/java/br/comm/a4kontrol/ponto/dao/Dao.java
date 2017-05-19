package br.comm.a4kontrol.ponto.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import br.comm.a4kontrol.ponto.helper.LogHelper;
import br.comm.a4kontrol.ponto.util.Constants;

/**
 * Created by geovan.goes on 09/05/2017.
 */

public abstract class DAO<T> extends SQLiteOpenHelper implements AbstractDAO<T>{

    private String TABLE_NAME;
    public static List<String> updateStatements = new ArrayList<String>();
    public static List<String> createStatements = new ArrayList<String>();
    private DAO dao;

    /**
     * Construtor
     * */
    DAO(Context context, String tableName, DAO dao) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);

        this.TABLE_NAME = tableName;
        this.dao = dao;

        DAO.createStatements.add(createTableQuery());
        DAO.updateStatements.addAll(updateTableQuery());
    }

    /**
     * Sobrescrita do método onCreate
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            for (String sql : createStatements){
                db.execSQL(sql);
            }
        } catch (NullPointerException nullPointer) {
            Log.d(this.getClass().getName(), "Query de create para a tabela "+getTableName()+" não encontrada");
        } catch (Exception e) {

        }
    }

    /**
     * Sobrescrita do método onUpgrade
     * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            for (String sql : updateStatements){
                db.execSQL(sql);
            }
            onCreate(db);
        } catch (NullPointerException nullPointer) {
            Log.d(this.getClass().getName(), "Query de update para a tabela "+getTableName()+" não encontrada");
        } catch (Exception e) {

        }
    }

    @Override
    public boolean insereOuAtualiza(T item) {
        try {
            boolean atualizaResult = atualiza(item);
            if (!atualizaResult){
                return insere(item);
            } else {
                return atualizaResult;
            }
        } catch(Exception e) {
            LogHelper.error(this, e, "");
            return false;
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
    public boolean deleta(String[] params) {
        try {
            getWritableDatabase().delete(getTableName(), getParamsName(), params);
            return true;
        } catch (Exception e) {
            LogHelper.error(this, e, "");
            return false;
        }
    }

    @Override
    public boolean atualiza(T item) {
        try {
            int linhasAfetas = getWritableDatabase().update(getTableName(), getContentValues(item), getParamsName(), getParams(item));
            if(linhasAfetas > 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        } catch (Exception e) {
            LogHelper.error(this, e, "");
            return false;
        }
    }

    @Override
    public boolean insere(T item){
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.insert(getTableName(), null, getContentValues(item));
            return true;
        } catch(Exception e) {
            LogHelper.error(this, e, "");
            return false;
        }
    }

    /**
     * Método responsável por retornar o nome da tabela
     * @return nome da tabela
     * */
    String getTableName(){
        return this.TABLE_NAME;
    }

    /**
     * Método resposável por comparar o Class recebido por parâmetro com o Class do Generics
     * @param clazz
     * @return boolean
     * */
    private boolean compare(Class clazz){

        if(clazz == getGenericName())
            return true;
        else
            return false;
    }

    /**
     * Método responsável por retornar o DAO correspondete ao model
     * @param clazz
     * @return AbstractDAO
     * */
    AbstractDAO getDAO(Class clazz){
        if (compare(clazz)) {
            return this;
        } else if (dao == null) {
            return null;
        } else {
            return dao.getDAO(clazz);
        }
    }

    /**
     * Método responsável por retornar o Class recebido como parametro via Generics
     * @return Class
     * */
    private Class getGenericName() {
        return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    abstract ContentValues getContentValues(T object);

    abstract String prepareConsultQuery(String param);

    abstract List<T> convertCursorToObject(Cursor cursor);

    abstract String getParamsName();

    abstract String createTableQuery();

    abstract List<String> updateTableQuery();

    abstract String[] getParams(T item);
}