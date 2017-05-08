package br.com.alura.agenda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.activity.FormularioActivity;
import br.com.alura.agenda.activity.ListaAlunosActivity;
import br.com.alura.agenda.helper.FormularioHelper;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by root on 06/05/17.
 */

public class ListaAlunosAdapter extends BaseAdapter {

    private List<Aluno> alunos;
    private Context context;


    public ListaAlunosAdapter(List<Aluno> alunos, Context context) {

        this.alunos = alunos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        Aluno aluno = alunos.get(position);

        TextView nome = (TextView) view.findViewById(R.id.item_nome);
        nome.setText(aluno.getNome());

        TextView telefone = (TextView) view.findViewById(R.id.item_telefone);
        telefone.setText(aluno.getTelefone());

        ImageView foto = (ImageView) view.findViewById(R.id.item_foto);
        FormularioHelper.carregaImagem(aluno.getCaminhoFoto(), foto);

        return view;
    }
}
