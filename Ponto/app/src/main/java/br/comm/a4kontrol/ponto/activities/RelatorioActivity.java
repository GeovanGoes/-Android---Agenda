package br.comm.a4kontrol.ponto.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Interval;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.comm.a4kontrol.ponto.R;
import br.comm.a4kontrol.ponto.dao.ConfiguracaoDao;
import br.comm.a4kontrol.ponto.dao.DAOChain;
import br.comm.a4kontrol.ponto.dao.FeriadoDao;
import br.comm.a4kontrol.ponto.dao.LancamentoDao;
import br.comm.a4kontrol.ponto.enumeration.PeriodosEnum;
import br.comm.a4kontrol.ponto.exception.DAOInexistenteException;
import br.comm.a4kontrol.ponto.helper.DataHelper;
import br.comm.a4kontrol.ponto.modelo.Configuracao;
import br.comm.a4kontrol.ponto.modelo.Feriado;
import br.comm.a4kontrol.ponto.modelo.Lancamento;
import br.comm.a4kontrol.ponto.util.Constants;

public class RelatorioActivity extends AppCompatActivity {


    private TextView horasACumprir;
    private TextView horasCumpridas;
    private TextView horasDeveriamEstarCumpridas;
    private TextView horasUteisMesAtual;
    private TextView horasUteisPeriodo;
    private LancamentoDao lancamentoDao;
    private FeriadoDao feriadoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_relatorio);
        horasACumprir = (TextView) findViewById(R.id.relatorio_horas_a_cumprir);
        horasCumpridas = (TextView) findViewById(R.id.relatorio_horas_cumpridas);
        horasDeveriamEstarCumpridas = (TextView) findViewById(R.id.relatorio_horas_deveriam_estar_cumpridas);
        horasUteisMesAtual = (TextView) findViewById(R.id.relatorio_horas_uteis_mes_atual);
        horasUteisPeriodo = (TextView) findViewById(R.id.relatorio_horas_uteis_periodo);



    }


}
