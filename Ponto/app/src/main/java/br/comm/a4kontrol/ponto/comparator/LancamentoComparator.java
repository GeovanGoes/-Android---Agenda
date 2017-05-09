package br.comm.a4kontrol.ponto.comparator;

import java.util.Comparator;

import br.comm.a4kontrol.ponto.modelo.Lancamento;

/**
 * Created by geovan.goes on 09/05/2017.
 */

public class LancamentoComparator implements Comparator<Lancamento> {
    @Override
    public int compare(Lancamento lancamento1, Lancamento lancamento2) {
        return lancamento1.getDateTime().compareTo(lancamento2.getDateTime());
    }
}
