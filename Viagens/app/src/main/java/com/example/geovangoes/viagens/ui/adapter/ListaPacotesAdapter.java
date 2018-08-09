package com.example.geovangoes.viagens.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geovangoes.viagens.R;
import com.example.geovangoes.viagens.model.Pacote;
import com.example.geovangoes.viagens.util.DiasUtil;
import com.example.geovangoes.viagens.util.MoedaUtil;
import com.example.geovangoes.viagens.util.ResourcesUtil;

import java.util.List;

/**
 * Created by geovangoes
 */
public class ListaPacotesAdapter extends RecyclerView.Adapter<PacoteViewHolder>
{

    private List<Pacote> pacotes;
    private final Context context;

    public ListaPacotesAdapter(List<Pacote> pacotes, Context context)
    {
        this.pacotes = pacotes;
        this.context = context;
    }

    @NonNull
    @Override
    public PacoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return new PacoteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pacote, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PacoteViewHolder pacoteViewHolder, int i)
    {
        if (pacotes != null && pacotes.size() > 0)
            pacoteViewHolder.bind(pacotes.get(i), context);
    }

    @Override
    public int getItemCount()
    {
        return pacotes.size();
    }
}
class PacoteViewHolder extends  RecyclerView.ViewHolder
{

    private ImageView imagem;
    private TextView local;
    private TextView dias;
    private TextView preco;

    public PacoteViewHolder(@NonNull View itemView)
    {
        super(itemView);
        imagem = itemView.findViewById(R.id.item_pacote_imagem);
        local = itemView.findViewById(R.id.item_pacote_local);
        dias = itemView.findViewById(R.id.item_pacote_dias);
        preco = itemView.findViewById(R.id.item_pacote_preco);
    }

    public void bind(Pacote pacote, Context context)
    {
        setImagem(pacote, context);
        setLocal(pacote);
        setDias(pacote);
        setPreco(pacote);
    }

    private void setPreco(Pacote pacote)
    {
        this.preco.setText(MoedaUtil.formatarMoedaPadraoBrasileiro(pacote.getPreco()));
    }

    private void setDias(Pacote pacote)
    {
        this.dias.setText(DiasUtil.formataDiasEmTexto(pacote.getDias()));
    }

    private void setLocal(Pacote pacote)
    {
        local.setText(pacote.getLocal());
    }

    private void setImagem(Pacote pacote, Context context)
    {
        imagem.setImageDrawable(ResourcesUtil.getDrawable(pacote.getImagem(), context));
    }
}