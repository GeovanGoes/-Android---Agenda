package br.com.alura.agenda;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.SupportMapFragment;

public class MapsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        FragmentManager manager = getFragmentManager();

        FragmentTransaction tx = manager.beginTransaction();

        SupportMapFragment supportMapFragment = new SupportMapFragment();

        FragmentTransaction replace = tx.replace(R.id.frame_mapa, supportMapFragment);
        tx.commit();
    }
}