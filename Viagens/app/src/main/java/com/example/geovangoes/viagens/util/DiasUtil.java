package com.example.geovangoes.viagens.util;

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
}
