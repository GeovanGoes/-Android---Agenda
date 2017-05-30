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
            return calendarDay.getDay() + "-" + (calendarDay.getMonth()+1) + "-" + calendarDay.getYear();
        }

        return null;
    }

    public static String formatarData(Date hoje) {
        if (hoje != null){
            return hoje.getDay() + "-" + hoje.getMonth() + "" + hoje.getYear();
        }
        return null;
    }

    public static String formatarData(DateTime hoje) {
        if (hoje != null){
            String data = hoje.getDayOfMonth() + "-" + (hoje.getMonthOfYear() + 1) + "-" + hoje.getYear();
            return data;
        }
        return null;
    }

    public static DateTime stringToDate(String data)
    {
        if(data != null && !data.equals(""))
        {
            String[] splitedString = data.split("-");

            if (splitedString.length == 3)
            {
                int ano = Integer.valueOf(splitedString[0]);
                int mes = Integer.valueOf(splitedString[1]) - 1;
                int dia = Integer.valueOf(splitedString[2]);

                return new DateTime(ano,mes,dia,0,0);
            }
        }
        return null;
    }

    public static void ordenarLancamentos(List<Lancamento> lancamentos){
        Collections.sort(lancamentos, new LancamentoComparator());
    }
}
