package br.com.alura.agenda.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.activity.DetalhesProvaActivity;
import br.com.alura.agenda.activity.ProvasActivity;
import br.com.alura.agenda.modelo.Prova;

/**
 * Created by geovan.goes on 17/05/2017.
 */

public class ListaProvasFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_provas_, container, false);


        Prova pt = new Prova("Portugues", "25/05/2017", Arrays.asList("Sujeito","Objeto","Objeto Indireto","Meu ovo"));
        Prova mat = new Prova("Matematica", "26/05/2017", Arrays.asList("Trigonometria","Nois metia","adição"));

        List<Prova> provas = Arrays.asList(pt, mat);

        final ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(), android.R.layout.simple_list_item_1, provas);

        ListView listProvas = (ListView) view.findViewById(R.id.provas_lista);

        listProvas.setAdapter(adapter);

        listProvas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Prova prova = (Prova) parent.getItemAtPosition(position);

                ProvasActivity activity = (ProvasActivity) getActivity();
                activity.selecionaProva(prova);
            }
        });
        return view;
    }
}
