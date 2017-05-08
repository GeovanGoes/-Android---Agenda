package br.comm.a4kontrol.ponto.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Date;
import java.util.List;

import br.comm.a4kontrol.ponto.R;
import br.comm.a4kontrol.ponto.dao.Dao;
import br.comm.a4kontrol.ponto.helper.DataHelper;
import br.comm.a4kontrol.ponto.modelo.Feriado;
import br.comm.a4kontrol.ponto.modelo.Lancamento;

public class Inicio extends AppCompatActivity {

    private Dao dao;
    private CalendarDay dataSelecionada = null;
    private TextView lancamentos;
    private TextView lancamento1;
    private TextView lancamento2;
    private TextView lancamento3;
    private TextView lancamento4;
    private Button verMais;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataSelecionada = new CalendarDay();

        int day = dataSelecionada.getDay();
        int month = dataSelecionada.getMonth();
        int year = dataSelecionada.getYear();

        dao = new Dao(this);

        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton marcacaoInstantanea = (FloatingActionButton) findViewById(R.id.marcacao_instantanea);
        marcacaoInstantanea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Inicio.this, "Opa marcacao inst", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton selecionarHorario = (FloatingActionButton) findViewById(R.id.selecionar_horario);
        selecionarHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Inicio.this, "Selecionar Horário", Toast.LENGTH_SHORT).show();
            }
        });

        MaterialCalendarView mcv = (MaterialCalendarView) findViewById(R.id.calendarView);
        mcv.setDateSelected(new Date(), true);
        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Toast.makeText(Inicio.this, date.toString(), Toast.LENGTH_SHORT).show();
                setDadosDoDiaNaView(date);
            }
        });

        CheckBox feriadoFolga = (CheckBox) findViewById(R.id.check_marcar_feriado);
        feriadoFolga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dao.ehFeriado(DataHelper.formatarData(dataSelecionada)))
                    marcarDataComoFeriado(dataSelecionada);
                else
                    desmarcarComoFeriado(dataSelecionada);
            }
        });
    }

    private void desmarcarComoFeriado(CalendarDay dataSelecionada) {
        dao.deletar(dao.getFeriado(DataHelper.formatarData(dataSelecionada)));
    }

    private void inicializarComponentesDaView() {
/*        lancamentos;
        lancamento1;
        lancamento2;
        lancamento3;
        lancamento4;
        verMais;
        status;*/
    }

    private boolean hojeEhFeriado() {
        Date hoje = new Date();
        return dao.ehFeriado(DataHelper.formatarData(hoje));
    }

    private void marcarDataComoFeriado(CalendarDay day) {
        Feriado feriado = new Feriado();
        feriado.setData(DataHelper.formatarData(day));
        dao.inserirFeriado(feriado);
        ocultarCamposDaView();
    }

    private void ocultarCamposDaView() {

    }

    private void setDadosDoDiaNaView(CalendarDay date) {
        String data = DataHelper.formatarData(date);
        List<Lancamento> lancamentosDoDia = dao.getLancamentosDoDia(data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
