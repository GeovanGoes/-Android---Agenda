package br.comm.a4kontrol.ponto;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import br.comm.a4kontrol.ponto.helper.DataHelper;
import br.comm.a4kontrol.ponto.modelo.Lancamento;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    /*@Test
    public void testaOrdenação(){
        List<Lancamento> lancamentos = new ArrayList<>();
        lancamentos.add(new Lancamento(1,"12:00","15-02-1992"));
        lancamentos.add(new Lancamento(1,"08:00","15-02-1992"));
        lancamentos.add(new Lancamento(1,"18:00","15-02-1992"));
        lancamentos.add(new Lancamento(1,"13:00","15-02-1992"));

        DataHelper.ordenarLancamentos(lancamentos);

        Log.d("","");

    }*/


    @Test
    public void testaFeriados(){

    }
}