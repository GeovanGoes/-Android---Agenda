package br.comm.a4kontrol.ponto.service;

import java.util.Date;

import br.comm.a4kontrol.ponto.to.ResultBaseFactoryTO;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by geovan.goes
 */

public interface PontoService
{
    @POST
    Call<ResultBaseFactoryTO> inserirUsuario(String userName);

    @POST(value = "/feriados")
    Call<ResultBaseFactoryTO> inserirFeriado(Date date);

    @GET(value = "/feriados")
    Call<ResultBaseFactoryTO> obterFeriados();

    @DELETE(value = "/feriados")
    Call<ResultBaseFactoryTO> removerFeriado(Date date);

    @GET(value = "/lancamentos")
    Call<ResultBaseFactoryTO> obterLancamentos();

    @POST(value = "/lancamentos")
    Call<ResultBaseFactoryTO> inserirLancamento(Date date);

    @GET
    Call<ResultBaseFactoryTO> getDominio(String nome);

}
