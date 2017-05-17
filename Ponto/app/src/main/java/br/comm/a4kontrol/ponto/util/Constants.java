package br.comm.a4kontrol.ponto.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by geovan.goes on 09/05/2017.
 */

public class Constants {

    public static final String DATABASE_NAME = "PONTO";
    public static final int DATABASE_VERSION = 408;
    public static final String CONFIGURACAO_INICIO_DO_CICLO = "INICIO_DO_CICLO";
    public static final String CONFIGURACAO_PERIODO_RELATORIO = "PERIODO_RELATORIO";

    public static final String TABELA_CONFIGURACAO = "Configuracoes";
    public static final String TABELA_FERIADO = "Feriados";
    public static final String TABELA_LANCAMENTO = "Lancamentos";

    public static List<String> tabelas = new ArrayList<String>()
    {
        {
            tabelas.add(TABELA_CONFIGURACAO);
            tabelas.add(TABELA_FERIADO);
            tabelas.add(TABELA_LANCAMENTO);
        }
    };

}
