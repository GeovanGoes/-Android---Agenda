package br.comm.a4kontrol.ponto;

import android.content.Context;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Interval;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.comm.a4kontrol.ponto.activities.RelatorioActivity;
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

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private LancamentoDao lancamentoDao;
    private FeriadoDao feriadoDao;

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("br.comm.a4kontrol.ponto", appContext.getPackageName());
    }

    @Test
    public void testQuantidadeHorasFeriados() throws DAOInexistenteException {

        Context appContext = InstrumentationRegistry.getTargetContext();
        DAOChain.configureDAO(appContext);
        DateTime now = DateTime.now();

        FeriadoDao dao = (FeriadoDao) DAOChain.getDAO(Feriado.class);
        boolean insere = dao.insereOuAtualiza(new Feriado(0, DataHelper.formatarData(now.plusWeeks(-2))));
        boolean insere1 = dao.insereOuAtualiza(new Feriado(0, DataHelper.formatarData(now.plusWeeks(-1))));
        boolean insere2 = dao.insereOuAtualiza(new Feriado(0, DataHelper.formatarData(now.plusWeeks(-3))));
        boolean insere3 = dao.insereOuAtualiza(new Feriado(0, DataHelper.formatarData(now.plusWeeks(-4))));
        boolean insere4 = dao.insereOuAtualiza(new Feriado(0, DataHelper.formatarData(now.plusWeeks(-5))));
        boolean insere5 = dao.insereOuAtualiza(new Feriado(0, DataHelper.formatarData(now.plusWeeks(-50))));

        configDao();
        Map<String, DateTime> stringDateTimeMap = obterRangeDateTime(now.plusMonths(-3), now, PeriodosEnum.Anual);

        Integer horasFeriadosPeriodo = getHorasFeriadosPeriodo(stringDateTimeMap);

        Integer integer = calcularHorasDoPeriodo(stringDateTimeMap);

        Log.d("","");

    }

    public void configDao(){
        try {
            lancamentoDao = (LancamentoDao) DAOChain.getDAO(Lancamento.class);
            feriadoDao = (FeriadoDao) DAOChain.getDAO(Feriado.class);
        } catch (DAOInexistenteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna as horas do período
     * */
    public Integer calcularHorasDoPeriodo(Map<String,DateTime> periodos){
        DateTime inicio = periodos.get(Constants.KEY_PERIODO_INICIO);
        DateTime fim = periodos.get(Constants.KEY_PERIODO_FIM);

        Integer utilHours = 0;

        while (inicio.isBefore(fim) || inicio.isEqual(fim)){
            if (inicio.getDayOfWeek() != DateTimeConstants.SATURDAY && inicio.getDayOfWeek() != DateTimeConstants.SUNDAY){
                utilHours = utilHours + Constants.HORAS_DIA;
            }
            inicio = inicio.plusDays(1);
        }
        return utilHours;
    }

    public Integer getHorasMesAtual(DateTime inicioCiclo){

        Integer result = 0;

        DateTime now = DateTime.now();
        DateTime workInicioCiclo = inicioCiclo;

        if (workInicioCiclo.isBeforeNow()) {
            while (workInicioCiclo.getMonthOfYear() != now.getMonthOfYear()) {
                workInicioCiclo = workInicioCiclo.plusMonths(1);
            }
        } else if (workInicioCiclo.isAfterNow()){
            while (workInicioCiclo.getMonthOfYear() != now.getMonthOfYear()) {
                workInicioCiclo = workInicioCiclo.plusMonths(-1);
            }
        }
        Map<String, DateTime> range = obterRangeDateTime(workInicioCiclo, workInicioCiclo.plusMonths(1), PeriodosEnum.Mensal);
        result = calcularHorasDoPeriodo(range);
        return result;
    }

    /**
     * Retorna a quantidade de horas de feriados e folgas de um período
     * */
    public Integer getHorasFeriadosPeriodo(Map<String,DateTime> periodos){
        Integer horasDeFeriado = 0;

        Interval interval = new Interval(periodos.get(Constants.KEY_PERIODO_INICIO), periodos.get(Constants.KEY_PERIODO_FIM));

        List<Feriado> listaFeriados = feriadoDao.lista(null);

        for (Feriado feriado : listaFeriados){
            DateTime dataDoFeriado = feriado.getDateTime();
            if (interval.contains(dataDoFeriado.getMillis())){
                horasDeFeriado = horasDeFeriado + Constants.HORAS_DIA;
            }
        }
        return horasDeFeriado;
    }

    public void getConfigurations() throws DAOInexistenteException {

        ConfiguracaoDao configuracaoDao = (ConfiguracaoDao) DAOChain.getDAO(Configuracao.class);
        List<Configuracao> listaCiclo = configuracaoDao.lista(Constants.CONFIGURACAO_INICIO_DO_CICLO);
        List<Configuracao> listaPeriodo = configuracaoDao.lista(Constants.CONFIGURACAO_PERIODO_RELATORIO);

        DateTime agora = DateTime.now();

    }

    public Map<String, DateTime> obterRangeDateTime(DateTime inicioCicloParam, DateTime dataDoRelatorio, PeriodosEnum periodoEnum){

        Map<String, DateTime> result = new HashMap<>();

        if (!dataDoRelatorio.isBefore(inicioCicloParam)){
            DateTime inicioCiclo = inicioCicloParam;
            DateTime fimDoCiclo = inicioCiclo.plusMonths(periodoEnum.getValor());

            Interval intervalo = new Interval(inicioCiclo,fimDoCiclo);

            boolean estaNoIntervaloInicial = intervalo.contains(dataDoRelatorio.getMillis());

            if (estaNoIntervaloInicial){
                result = new HashMap<>();
                result.put(Constants.KEY_PERIODO_INICIO,inicioCiclo);
                result.put(Constants.KEY_PERIODO_FIM,fimDoCiclo);
                return result;
            } else {
                while (!estaNoIntervaloInicial){
                    inicioCiclo = inicioCiclo.plusMonths(periodoEnum.getValor());
                    fimDoCiclo = inicioCiclo.plusMonths(periodoEnum.getValor());
                    intervalo = new Interval(inicioCiclo,fimDoCiclo);
                    estaNoIntervaloInicial = intervalo.contains(dataDoRelatorio.getMillis());
                }
                result.put(Constants.KEY_PERIODO_INICIO, inicioCiclo);
                result.put(Constants.KEY_PERIODO_FIM, fimDoCiclo);
            }
        }
        return result;
    }
}
