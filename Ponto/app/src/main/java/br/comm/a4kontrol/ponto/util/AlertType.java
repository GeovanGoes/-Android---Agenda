package br.comm.a4kontrol.ponto.util;

/**
 * Created by geovan.goes on 10/05/2017.
 */
public enum AlertType {

    SUCCESS(1),
    ERROR(2),
    WARNING(3);

    private final long valor;

    AlertType(long valorOpcao) {
        valor = valorOpcao;
    }

    public long getValor() {
        return valor;
    }
}
