package br.comm.a4kontrol.ponto.helper;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Date;

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
}
