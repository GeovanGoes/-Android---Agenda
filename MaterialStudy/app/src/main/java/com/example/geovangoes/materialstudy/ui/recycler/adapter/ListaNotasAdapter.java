package com.example.geovangoes.materialstudy.ui.recycler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.geovangoes.materialstudy.R;
import com.example.geovangoes.materialstudy.model.Nota;
import com.example.geovangoes.materialstudy.ui.recycler.adapter.listener.OnItemClickListener;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by geovangoes
 */
public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder>
{

    private final Context context;
    private final List<Nota> notas;
    private OnItemClickListener onItemClickListener;
    private static int contaViewHolder = 0;

    public ListaNotasAdapter(Context context, List<Nota> notas)
    {
        this.context = context;
        this.notas = notas;
    }

    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        contaViewHolder++;
        Log.i("recycleview adapter ", "quantidade viewHolder: " + contaViewHolder);
        return new NotaViewHolder(LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotaViewHolder holder, int position)
    {
        Nota nota = notas.get(position);
        holder.vincula(nota);
    }

    @Override
    public int getItemCount()
    {
        return notas.size();
    }

    public void adiciona (Nota... nota)
    {
        notas.addAll(Arrays.asList(nota));
        notifyDataSetChanged();
    }


    /**
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    /***
     *
     * @param posicao
     * @param nota
     */
    public void altera(int posicao, Nota nota)
    {
        notas.set(posicao, nota);
        notifyItemChanged(posicao);
    }

    /***
     *
     * @param position
     */
    public void remove(int position)
    {
        notas.remove(position);
        notifyItemRemoved(position);
    }

    /***
     *
     * @param posicaoInicial
     * @param posicaoFinal
     */
    public void troca(int posicaoInicial, int posicaoFinal)
    {
        Collections.swap(notas, posicaoInicial, posicaoFinal);
        notifyItemMoved(posicaoInicial, posicaoFinal);
    }

    /**
     *
     */
    class NotaViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView titulo;
        private final TextView descricao;
        private Nota nota;

        public NotaViewHolder(View itemView)
        {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_nota_titulo);
            descricao = itemView.findViewById(R.id.item_nota_descricao);
            itemView.setOnClickListener((view) -> onItemClickListener.onItemClick(nota, getAdapterPosition()));
        }

        public void vincula(Nota nota)
        {
            this.titulo.setText(nota.getTitulo());
            this.descricao.setText(nota.getDescricao());
            this.nota = nota;
        }
    }
}
