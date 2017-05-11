package br.comm.a4kontrol.ponto.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import br.comm.a4kontrol.ponto.util.QuestionDialogCallback;
import br.comm.a4kontrol.ponto.util.SingleActionDialogCallback;

/**
 * Created by geovan.goes on 11/05/2017.
 */

public class DialogHelper {

    /**
     * Método responsável por exibir uma caixa de diálogo com duas opções, uma positiva e uma negativa
     * */
    public static void showQuestionDialog(Context context, int titulo, int mensagem, DialogIcon icon, int textoBotaoPositivo, int textoBotaoNegativo, final QuestionDialogCallback questionDialogCallback){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo);
        builder.setIcon(icon.getValor());
        builder.setMessage(mensagem);

        builder.setPositiveButton(textoBotaoPositivo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                questionDialogCallback.escolha(true);
            }
        });
        builder.setNegativeButton(textoBotaoNegativo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                questionDialogCallback.escolha(false);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showMessageDialog(Context context, int titulo, int message, DialogIcon icon, int textoBotao, final SingleActionDialogCallback callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo);
        builder.setIcon(icon.getValor());
        builder.setMessage(message);

        builder.setPositiveButton(textoBotao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null)
                    callback.onActionClicked();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
