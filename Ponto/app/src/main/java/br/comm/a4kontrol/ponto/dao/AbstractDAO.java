package br.comm.a4kontrol.ponto.dao;

import java.util.List;

/**
 * Created by geovan.goes on 09/05/2017.
 */
public interface AbstractDAO<T> {

    /**
     * Método responsável por insere um objeto no banco de dados
     * */
    void insere(T item);

    /**
     * Método responsável por listar os registros de uma tabela
     * */
    List<T> lista(String param);

    /**
     * Método responsável por deletar um registro de uma tabela
     * */
    void deleta(String[] params);

    /**
     * Método responsável por atualizar um registro de uma tabela
     * */
    void atualiza(String[] params);
}
