package br.com.alura.agenda.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.alura.agenda.Localizador;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by geovan.goes on 22/05/2017.
 */

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback{


    @Override
    public void onCreate(Bundle bundle) {

        getMapAsync(this);

        super.onCreate(bundle);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = pegaCordenadaDoEndereco("Avenida brigadeiro faria lima");
        if (latLng != null){
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng,17);
            googleMap.moveCamera(update);

            AlunoDAO dao = new AlunoDAO(getContext());

            List<Aluno> alunos = dao.getAlunos();
            for (Aluno aluno : alunos){
                String endereco = aluno.getEndereco();
                if (endereco != null){
                    LatLng localAluno = pegaCordenadaDoEndereco(endereco);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(localAluno);
                    markerOptions.title(aluno.getNome());
                    markerOptions.snippet(String.valueOf(aluno.getNota()));
                    googleMap.addMarker(markerOptions);
                }
            }
            dao.close();
        }

        Localizador localizador = new Localizador(getContext(), googleMap);
    }

    private LatLng pegaCordenadaDoEndereco(String endereco){
        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> results = geocoder.getFromLocationName(endereco, 1);

            if (results.size() > 0){
                Address address = results.get(0);
                LatLng posicaoEscols = new LatLng(address.getLatitude(), address.getLongitude());
                return posicaoEscols;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
