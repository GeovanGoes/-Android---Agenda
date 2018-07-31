package br.com.alura.leilao.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by geovangoes
 */
public class LeilaoTest
{

    @Test
    public void getDescricao() throws Exception
    {
        Leilao leilao = new Leilao("Teste");
        assertEquals("Teste", leilao.getDescricao());
    }

    @Test
    public void getMaiorLance()
    {
        Leilao teste = new Leilao("Teste");
        teste.propoe(new Lance(new Usuario("Juaum"), 300.0));
        teste.propoe(new Lance(new Usuario("Jusé"), 100.0));
        teste.propoe(new Lance(new Usuario("Maria"), 200.0));
        assertEquals(300.0, teste.getMaiorLance(), 0.0001);

        teste = new Leilao("Teste");
        teste.propoe(new Lance(new Usuario("Juaum"), 100.0));
        teste.propoe(new Lance(new Usuario("Jusé"), 300.0));
        teste.propoe(new Lance(new Usuario("Maria"), 100.0));
        assertEquals(300.0, teste.getMaiorLance(), 0.0001);

        teste = new Leilao("Teste");
        teste.propoe(new Lance(new Usuario("Juaum"), 100.0));
        teste.propoe(new Lance(new Usuario("Jusé"), 200.0));
        teste.propoe(new Lance(new Usuario("Maria"), 300.0));
        assertEquals(300.0, teste.getMaiorLance(), 0.0001);

        teste = new Leilao("Teste");
        teste.propoe(new Lance(new Usuario("Juaum"), 300.0));
        teste.propoe(new Lance(new Usuario("Jusé"), 200.0));
        teste.propoe(new Lance(new Usuario("Maria"), 100.0));
        assertEquals(300.0, teste.getMaiorLance(), 0.0001);
    }

    @Test
    public void getMenorLance()
    {
        Leilao teste = new Leilao("Teste");
        teste.propoe(new Lance(new Usuario("Juaum"), 300.0));
        teste.propoe(new Lance(new Usuario("Jusé"), 100.0));
        teste.propoe(new Lance(new Usuario("Maria"), 200.0));
        assertEquals(100.0, teste.getMenorLance(), 0.0001);

        teste = new Leilao("Teste");
        teste.propoe(new Lance(new Usuario("Juaum"), 100.0));
        teste.propoe(new Lance(new Usuario("Jusé"), 100.0));
        teste.propoe(new Lance(new Usuario("Maria"), 100.0));
        assertEquals(100.0, teste.getMenorLance(), 0.0001);

        teste = new Leilao("Teste");
        teste.propoe(new Lance(new Usuario("Juaum"), 100.0));
        teste.propoe(new Lance(new Usuario("Jusé"), 200.0));
        teste.propoe(new Lance(new Usuario("Maria"), 300.0));
        assertEquals(100.0, teste.getMenorLance(), 0.0001);

        teste = new Leilao("Teste");
        teste.propoe(new Lance(new Usuario("Juaum"), 300.0));
        teste.propoe(new Lance(new Usuario("Jusé"), 200.0));
        teste.propoe(new Lance(new Usuario("Maria"), 100.0));
        assertEquals(100.0, teste.getMenorLance(), 0.0001);
    }
}