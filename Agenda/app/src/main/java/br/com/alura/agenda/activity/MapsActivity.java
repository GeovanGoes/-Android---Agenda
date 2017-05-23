package br.com.alura.agenda.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.SupportMapFragment;

import br.com.alura.agenda.R;
import br.com.alura.agenda.fragment.MapsFragment;

public class MapsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction tx = manager.beginTransaction();

        MapsFragment mapsFragment = new MapsFragment();

        FragmentTransaction replace = tx.replace(R.id.frame_mapa, mapsFragment);
        tx.commit();
    }
}