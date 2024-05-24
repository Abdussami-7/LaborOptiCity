package com.example.laboropticity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapTestActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_test);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragmentTest);
        if (mapFragment != null){
            Toast.makeText(getApplicationContext(), "map fragg not null", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Map frag is null", Toast.LENGTH_SHORT).show();
        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
//        LatLng loc = new LatLng(55.6761, 12.5683);
        gMap = googleMap;
        LatLng loc = new com.google.android.gms.maps.model.LatLng(55.6761,12.5683);
        gMap.addMarker(new MarkerOptions().position(loc).title("My Loc"));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 12));
    }
}