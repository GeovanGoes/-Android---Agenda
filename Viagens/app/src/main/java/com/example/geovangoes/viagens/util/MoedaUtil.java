package com.example.geovangoes.viagens.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 * Created by geovangoes
 */
public class MoedaUtil
{

    public static final String LANGUAGE = "pt";
    public static final String COUNTRY = "br";
    public static final String FORMATO_PADRAO = "R$";
    public static final String FORMATO_COM_ESPACO = "R$ ";

    public static String formatarMoedaPadraoBrasileiro(BigDecimal preco)
    {
        return DecimalFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(preco).replace(FORMATO_PADRAO, FORMATO_COM_ESPACO);
    }
}
