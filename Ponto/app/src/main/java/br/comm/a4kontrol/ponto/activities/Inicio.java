package br.comm.a4kontrol.ponto.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import br.comm.a4kontrol.ponto.R;
import br.comm.a4kontrol.ponto.dao.Dao;

public class Inicio extends AppCompatActivity {

    private Dao dao;
    private CalendarDay dataSelecionada = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = new Dao(this);

        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.botao_adicionar_lancammento);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                Intent adicionar = new Intent(Inicio.this, AdicionarLancamento.class);
                adicionar.putExtra("data", dataSelecionada);
                startActivity(adicionar);
            }
        });*/

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

        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Toast.makeText(Inicio.this, date.toString(), Toast.LENGTH_SHORT).show();
                trocarDadosDoDia(date);
            }
        });

        CheckBox feriadoFolga = (CheckBox) findViewById(R.id.check_marcar_feriado);
        feriadoFolga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marcarDataComoFeriado(dataSelecionada);
            }
        });
    }

    private void marcarDataComoFeriado(CalendarDay day) {

    }

    private void trocarDadosDoDia(CalendarDay date) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
