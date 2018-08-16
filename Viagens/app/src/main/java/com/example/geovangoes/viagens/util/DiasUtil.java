package com.example.geovangoes.viagens.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by geovangoes
 */
public class DiasUtil
{
    public static final String SINGULAR = "dia";
    public static final String PLURAL = SINGULAR + "s";
    public static final String ESPACO = " ";

    public static String formataDiasEmTexto(int dias)
    {
        return dias + (dias > 1 ? ESPACO + PLURAL : ESPACO + SINGULAR);
    }

    public static Calendar somaDias(int dias, Calendar date)
    {
        date.add(Calendar.DATE, dias);
        return date;
    }

    public static String formatarData(Calendar data)
    {
        SimpleDateFormat formatoBrasileiro = new SimpleDateFormat("dd/MM");
        return formatoBrasileiro.format(data.getTime());
    }
}
