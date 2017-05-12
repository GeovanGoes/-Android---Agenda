package br.comm.a4kontrol.ponto.enumeration;

/**
 * Created by geovan.goes on 12/05/2017.
 */
public enum PeriodosEnum {

    Mensal(0),
    Bimestral(1),
    Trimestral(2),
    Quadrimestral(3),
    Quimestral(4),
    Semestral(5),
    Anual(6);

    private final int valor;

    PeriodosEnum(int valorOpcao) {
        valor = valorOpcao;
    }

    public int getValor() {
        return valor;
    }
}
