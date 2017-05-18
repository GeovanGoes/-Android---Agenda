package br.comm.a4kontrol.ponto.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import br.comm.a4kontrol.ponto.R;
import br.comm.a4kontrol.ponto.dao.ConfiguracaoDao;
import br.comm.a4kontrol.ponto.dao.DAOChain;
import br.comm.a4kontrol.ponto.enumeration.PeriodosEnum;
import br.comm.a4kontrol.ponto.exception.DAOInexistenteException;
import br.comm.a4kontrol.ponto.helper.DialogHelper;
import br.comm.a4kontrol.ponto.helper.DialogIcon;
import br.comm.a4kontrol.ponto.modelo.Configuracao;
import br.comm.a4kontrol.ponto.util.Constants;
import br.comm.a4kontrol.ponto.util.SingleActionDialogCallback;

public class Configuracoes extends AppCompatActivity {

    public static String data = "";
    private ConfiguracaoDao configuracaoDao;
    private Spinner spinnerPeriodo;
    private String inicioCiclo;
    private TextView textViewInicioCiclo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        try {
            configuracaoDao = (ConfiguracaoDao) DAOChain.getDAO(Configuracao.class);
        } catch (DAOInexistenteException e) {
            e.printStackTrace();
        }

        textViewInicioCiclo = (TextView) findViewById(R.id.Text_inicio_ciclo);
        List<Configuracao> listaConfiguracao = configuracaoDao.lista(Constants.CONFIGURACAO_INICIO_DO_CICLO);
        if (listaConfiguracao.size() > 0){
            Configuracao configuracao = listaConfiguracao.get(0);
            inicioCiclo = configuracao.getValue();
            textViewInicioCiclo.setText(inicioCiclo);
        } else {
            inicioCiclo = null;
            textViewInicioCiclo.setText("-");
        }

        spinnerPeriodo = (Spinner) findViewById(R.id.spinner_periodo);

        List<String> itensSpinner = new ArrayList<String>();
        itensSpinner.add(PeriodosEnum.Mensal.name());
        itensSpinner.add(PeriodosEnum.Bimestral.name());
        itensSpinner.add(PeriodosEnum.Trimestral.name());
        itensSpinner.add(PeriodosEnum.Quadrimestral.name());
        itensSpinner.add(PeriodosEnum.Quimestral.name());
        itensSpinner.add(PeriodosEnum.Semestral.name());
        itensSpinner.add(PeriodosEnum.Anual.name());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, itensSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriodo.setAdapter(adapter);


        listaConfiguracao = configuracaoDao.lista(Constants.CONFIGURACAO_PERIODO_RELATORIO);
        if (listaConfiguracao.size() > 0){
            Configuracao configuracao = listaConfiguracao.get(0);
            String periodo = configuracao.getValue();

            int position = 0;
            for (PeriodosEnum periodoEnum : PeriodosEnum.values()){
                if (periodoEnum.name().equals(periodo)){
                    position = periodoEnum.getValor();
                }
            }

            spinnerPeriodo.setSelection(position);
        }



        ImageView imageCalendar = (ImageView) findViewById(R.id.image_calendar);

        final DateTime now = DateTime.now();

        imageCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Configuracoes.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Toast.makeText(Configuracoes.this, dayOfMonth + "-" + (month+1) + "-" + year, Toast.LENGTH_SHORT).show();

                        data = dayOfMonth + "-" + (month+1) + "-" + year;
                        textViewInicioCiclo.setText(data);
                    }
                }, now.getYear(), now.getMonthOfYear() > 0 ? now.getMonthOfYear() - 1 : now.getMonthOfYear(), now.getDayOfMonth());
                datePickerDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_configuracoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_configuracoes_ok){
            if (data != null && !data.equals("")) {
                configuracaoDao.insereOuAtualiza(new Configuracao(Constants.CONFIGURACAO_INICIO_DO_CICLO,data));
            }

            String selectedItem = (String) spinnerPeriodo.getSelectedItem();

            if (selectedItem != null){
                configuracaoDao.insereOuAtualiza(new Configuracao(Constants.CONFIGURACAO_PERIODO_RELATORIO, selectedItem));
            }

            DialogHelper.showMessageDialog(this, R.string.Titulo_sucesso, R.string.Mensagem_configuracoes_salvas_com_sucesso, DialogIcon.SUCCESS, R.string.Label_Ok, new SingleActionDialogCallback() {
                @Override
                public void onActionClicked() {
                    finish();
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
