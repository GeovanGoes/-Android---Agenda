package br.comm.a4kontrol.ponto.helper;

import android.util.Log;

/**
 * Created by geovan.goes on 09/05/2017.
 */

public class LogHelper {

    public static void error(Object object, Exception e, String extra){
        Log.e(object != null ? object.getClass().getName() : "", e.getMessage() + " : " + extra);
    }

}
