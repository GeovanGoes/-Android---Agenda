package br.comm.a4kontrol.ponto.helper;

import br.comm.a4kontrol.ponto.R;

/**
 * Created by geovan.goes on 10/05/2017.
 */
public enum DialogIcon {

    SUCCESS(R.mipmap.ic_sucesso),
    ERROR(R.mipmap.ic_erro),
    WARNING(R.mipmap.ic_warning),
    INFO(R.mipmap.ic_info);

    private final int valor;

    DialogIcon(int valorOpcao) {
        valor = valorOpcao;
    }

    public int getValor() {
        return valor;
    }
}
