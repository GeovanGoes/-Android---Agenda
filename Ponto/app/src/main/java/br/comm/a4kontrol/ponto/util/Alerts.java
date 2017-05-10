package br.comm.a4kontrol.ponto.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import br.comm.a4kontrol.ponto.R;

import static android.R.id.message;

/**
 * Created by geovan.goes on 10/05/2017.
 */

public class Alerts {
    public static void alert(final Context activity, String actionLeft, String actionRight, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setMessage(message);

        builder.setPositiveButton(actionLeft, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.setNegativeButton(actionRight, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
