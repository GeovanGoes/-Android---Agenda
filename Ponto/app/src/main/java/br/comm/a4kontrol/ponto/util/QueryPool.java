package br.comm.a4kontrol.ponto.util;

/**
 * Created by geovan.goes on 15/05/2017.
 */

public class QueryPool {

    public static String createTabelLancamentos(){
        String createTable = "CREATE TABLE "+
                Constants.TABELA_LANCAMENTO+
                " (" +
                "id INTEGER PRIMARY KEY," +
                "horario TEXT NOT NULL, " +
                "data TEXT NOT NULL);";
        return createTable;
    }
}
