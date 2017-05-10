package br.comm.a4kontrol.ponto.helper;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.comm.a4kontrol.ponto.comparator.LancamentoComparator;
import br.comm.a4kontrol.ponto.modelo.Lancamento;

/**
 * Created by geovan.goes on 08/05/2017.
 */

public class DataHelper {

    public static String formatarData(CalendarDay calendarDay){
        if (calendarDay != null){
            return calendarDay.getDay() + "-" + calendarDay.getMonth() + "-" + calendarDay.getYear();
        }

        return null;
    }

    public static String formatarData(Date hoje) {
        if (hoje != null){
            return hoje.getDay() + "-" + hoje.getMonth() + "" + hoje.getYear();
        }
        return null;
    }

    public static void ordenarLancamentos(List<Lancamento> lancamentos){
        Collections.sort(lancamentos, new LancamentoComparator());
    }
}
