package br.comm.a4kontrol.ponto.callbacks;

import android.content.Context;

import br.comm.a4kontrol.ponto.to.ResultBaseFactoryTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by geovan.goes
 */

public class ObterFeriadosCallback implements Callback<ResultBaseFactoryTO>
{

    private Context context;

    public ObterFeriadosCallback(Context context)
    {
        this.context = context;
    }

    @Override
    public void onResponse(Call<ResultBaseFactoryTO> call, Response<ResultBaseFactoryTO> response)
    {

    }

    @Override
    public void onFailure(Call<ResultBaseFactoryTO> call, Throwable t)
    {

    }
}
