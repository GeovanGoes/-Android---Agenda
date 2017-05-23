package br.comm.a4kontrol.ponto.util;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.Weeks;

import br.comm.a4kontrol.ponto.dao.LancamentoDao;
import br.comm.a4kontrol.ponto.helper.DataHelper;
import br.comm.a4kontrol.ponto.modelo.Lancamento;

/**
 * Created by geovan on 20/05/17.
 */

public class MassaDeDados {

    public static void insereVariosLancamentosParaTesteCaralho(LancamentoDao lancamentoDao){
        DateTime now = DateTime.now();
        for (int i = 1 ; i < 110 ; i++){
            //Log.d("dia",now.toString());

            int diaDaSemana = now.dayOfWeek().get();
            int sabado = DateTimeConstants.SATURDAY;
            int domingo = DateTimeConstants.SUNDAY;
            boolean hojeEhSabado = diaDaSemana == sabado;
            boolean hojeEhDomingo = diaDaSemana == domingo;

            if (!hojeEhDomingo && !hojeEhSabado) {
                Log.d("Inserindo:",String.valueOf(diaDaSemana));
                String data = DataHelper.formatarData(now);
                boolean insere = lancamentoDao.insere(new Lancamento(0, "08:00", data));
                boolean insere1 = lancamentoDao.insere(new Lancamento(0, "12:00", data));
                boolean insere2 = lancamentoDao.insere(new Lancamento(0, "13:00", data));
                boolean insere3 = lancamentoDao.insere(new Lancamento(0, "18:00", data));

                //Log.d(">>>>>>>>>>>>>>>>",data);
            }
            now = now.plusDays(-1);
        }
    }
}
