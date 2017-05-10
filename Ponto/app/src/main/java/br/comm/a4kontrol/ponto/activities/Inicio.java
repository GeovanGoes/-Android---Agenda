package br.comm.a4kontrol.ponto.activities;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

import br.comm.a4kontrol.ponto.R;
import br.comm.a4kontrol.ponto.dao.FeriadoDao;
import br.comm.a4kontrol.ponto.dao.LancamentoDao;
import br.comm.a4kontrol.ponto.helper.DataHelper;
import br.comm.a4kontrol.ponto.helper.LogHelper;
import br.comm.a4kontrol.ponto.modelo.Feriado;
import br.comm.a4kontrol.ponto.modelo.Lancamento;

public class Inicio extends AppCompatActivity {

    private LancamentoDao lancamentosDao;
    private FeriadoDao feriadoDao;
    private CalendarDay dataSelecionada = null;
    private TextView lancamentos;
    private TextView lancamento1;
    private TextView lancamento2;
    private TextView lancamento3;
    private TextView lancamento4;
    private Button verMais;
    private TextView status;
    private CheckBox feriadoFolga;
    private FloatingActionButton marcacaoInstantanea;
    private FloatingActionButton selecionarHorario;
    private FloatingActionsMenu floatingActionsMenu;
    private MaterialCalendarView materialCalendarView;

    private List<Lancamento> lancamentosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inicio);

        dataSelecionada = CalendarDay.today();
        lancamentosDao = new LancamentoDao(this);
        feriadoDao = new FeriadoDao(this);

        findViews();

        inicializarView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        alterarEstadoDaView(dataSelecionada);
    }

    public void inicializarView(){
        setupToolbar();
        setActionsFloatingButtons();
        setActionsCalendar();
        setActionsCheckbox();
    }

    public void alterarEstadoDaView(CalendarDay day){

        if( ehFeriado(day)) {
            feriadoFolga.setChecked(true);
        } else {
            feriadoFolga.setChecked(false);
        }
        setValueLancamentos(lancamentosDao.lista(DataHelper.formatarData(dataSelecionada)));
    }

    public void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Método responsável por instantiar todas as views que compoem a tela
     * */
    public void findViews(){
        feriadoFolga = (CheckBox) findViewById(R.id.check_marcar_feriado);
        lancamentos = (TextView) findViewById(R.id.text_lancammentos);
        lancamento1 = (TextView) findViewById(R.id.resumo_lancammento1);
        lancamento2 = (TextView) findViewById(R.id.resumo_lancammento2);
        lancamento3 = (TextView) findViewById(R.id.resumo_lancammento3);
        lancamento4 = (TextView) findViewById(R.id.resumo_lancammento4);
        verMais = (Button) findViewById(R.id.btn_ver_mais);
        status = (TextView) findViewById(R.id.label_status);
        materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        floatingActionsMenu = (FloatingActionsMenu) findViewById(R.id.floating_menu);
        marcacaoInstantanea = (FloatingActionButton) findViewById(R.id.marcacao_instantanea);
        selecionarHorario = (FloatingActionButton) findViewById(R.id.selecionar_horario);
    }

    /**
     * Método responsável por setar os listeners do checkbox
     * */
    public void setActionsCheckbox(){
        feriadoFolga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (!ehFeriado(dataSelecionada)) {

                        if(lancamentosList.size()>0){

                            AlertDialog.Builder builder = new AlertDialog.Builder(Inicio.this);
                            builder.setTitle("Tem certeza que deseja marcar essa data com um feriado?");
                            builder.setIcon(android.R.drawable.btn_star_big_on);
                            builder.setMessage("Fazendo isso você removerá desta data todos os lançamentos de horas.");

                            builder.setPositiveButton("Marcar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    marcarDataComoFeriado(dataSelecionada);
                                }
                            });
                            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            marcarDataComoFeriado(dataSelecionada);
                        }
                    }
                    else
                        desmarcarComoFeriado(dataSelecionada);
                }catch(Exception e){
                    LogHelper.error(this, e, null);
                }
            }
        });
    }

    /**
     * Método responsável por setar os listeners do calendário
     * */
    public void setActionsCalendar(){
        materialCalendarView.setDateSelected(new Date(), true);
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                dataSelecionada = date;
                alterarEstadoDaView(dataSelecionada);
            }
        });
    }

    /**
     * Método responsável por setar os listeners dos floating buttons
     * */
    public void setActionsFloatingButtons(){
        marcacaoInstantanea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ehFeriado(dataSelecionada)){
                    Toast.makeText(Inicio.this, "Você não pode lancar horas num feriado ou folga.", Toast.LENGTH_SHORT).show();
                } else {
                    DateTime hoje = DateTime.now();
                    String horario = hoje.getHourOfDay() + ":" + hoje.getMinuteOfHour();
                    String data = DataHelper.formatarData(CalendarDay.today());
                    lancamentosDao.insere(new Lancamento(0,horario,data));
                    materialCalendarView.clearSelection();
                    materialCalendarView.setDateSelected(hoje.toDate(),true);

                    dataSelecionada = CalendarDay.today();
                    alterarEstadoDaView(dataSelecionada);
                }
                floatingActionsMenu.collapse();
            }
        });

        selecionarHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ehFeriado(dataSelecionada)){
                    DateTime now = DateTime.now();
                    TimePickerDialog timePickerDialog = new TimePickerDialog(Inicio.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String horario = hourOfDay + ":" + (minute < 10 ? "0" + minute : minute);
                            lancamentosDao.insere(new Lancamento(0,horario,DataHelper.formatarData(dataSelecionada)));
                            alterarEstadoDaView(dataSelecionada);
                        }
                    },now.getHourOfDay(), now.getMinuteOfHour(), true);
                    timePickerDialog.show();
                } else {
                    Toast.makeText(Inicio.this, "Você não pode lancar horas num feriado ou folga.", Toast.LENGTH_SHORT).show();
                }
                floatingActionsMenu.collapse();
            }
        });
    }

    /**
     * Método que desmarca um dia como feriado no BD
     * */
    private void desmarcarComoFeriado(CalendarDay dataSelecionada) {
        feriadoDao.deleta(new String[]{DataHelper.formatarData(dataSelecionada)});
    }

    /**
     * Método responsável por verificar se uma data é feriado
     * */
    private boolean ehFeriado(CalendarDay date){
        List<Feriado> feriados = feriadoDao.lista(DataHelper.formatarData(date));
        if (feriados != null &&  feriados.size() > 0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método resposnável por marcar um dia como feriado
     * */
    private void marcarDataComoFeriado(CalendarDay day) {
        Feriado feriado = new Feriado();
        feriado.setData(DataHelper.formatarData(day));
        lancamentosDao.deleta(new String[]{DataHelper.formatarData(day)});
        feriadoDao.insere(feriado);
        alterarEstadoDaView(dataSelecionada);
    }

    /**
     * Método responsável por setar os hotrários de lançamento
     * */
    private void setValueLancamentos(List<Lancamento> lancamentos){
        DataHelper.ordenarLancamentos(lancamentos);
        lancamentosList = lancamentos;
        if(lancamentos.size() == 0){
            this.lancamentos.setVisibility(View.GONE);
            this.verMais.setVisibility(View.GONE);
        }else{
            this.lancamentos.setVisibility(View.VISIBLE);
            this.verMais.setVisibility(View.VISIBLE);
        }
        lancamento1.setText(lancamentos.size() > 0 ? lancamentos.get(0).getHorario() : "");
        lancamento2.setText(lancamentos.size() > 1 ? lancamentos.get(1).getHorario() : "");
        lancamento3.setText(lancamentos.size() > 2 ? lancamentos.get(2).getHorario() : "");
        lancamento4.setText(lancamentos.size() > 3 ? lancamentos.get(3).getHorario() : "");
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