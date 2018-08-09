package com.example.geovangoes.viagens.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.example.geovangoes.viagens.model.Pacote;

/**
 * Created by geovangoes
 */
public class ResourcesUtil
{

    public static final String DRAWABLE = "drawable";

    public static Drawable getDrawable(String imagem, Context context)
    {
        Resources resources = context.getResources();
        int idImagem = resources.getIdentifier(imagem, DRAWABLE, context.getPackageName());
        return resources.getDrawable(idImagem);
    }
}
