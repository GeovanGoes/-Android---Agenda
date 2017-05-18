package br.comm.a4kontrol.ponto.dao;

import android.content.Context;

import br.comm.a4kontrol.ponto.exception.DAOInexistenteException;

/**
 * Created by geovan.goes on 18/05/2017.
 * Classe responsável por centralizar as DAOS da aplicação
 */
public class DAOChain {

    private static LancamentoDao lancamentoDao;
    private static FeriadoDao feriadoDao;
    private static ConfiguracaoDao configDao;

    /**
     * Chamar esse método no inicio do app
     * */
    public static void configureDAO(Context context){

        /**
         * montagem da corrente, sempre adicionar novos objetos antes do primeiro e seguir a mesma lógica.
         * */

        lancamentoDao = new LancamentoDao(context, null);
        feriadoDao = new FeriadoDao(context, lancamentoDao);
        configDao = new ConfiguracaoDao(context, feriadoDao);
    }

    /**
     * Método responsável por retornar uma dao baseada no objeto que será persistido
     * */
    public static AbstractDAO getDAO(Class clazz) throws DAOInexistenteException {
        AbstractDAO dao = configDao.getDAO(clazz);
        if (dao == null){
            throw new DAOInexistenteException();
        } else {
            return dao;
        }
    }
}
