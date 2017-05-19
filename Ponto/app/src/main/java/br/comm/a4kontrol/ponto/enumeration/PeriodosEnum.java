package br.comm.a4kontrol.ponto.enumeration;

/**
 * Created by geovan.goes on 12/05/2017.
 */
public enum PeriodosEnum {

    Mensal(1),
    Bimestral(2),
    Trimestral(3),
    Quadrimestral(4),
    Quimestral(5),
    Semestral(6),
    Anual(12);

    private final int valor;

    PeriodosEnum(int valorOpcao) {
        valor = valorOpcao;
    }

    public int getValor() {
        return valor;
    }
}
