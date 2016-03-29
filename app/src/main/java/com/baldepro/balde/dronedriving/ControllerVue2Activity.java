package com.baldepro.balde.dronedriving;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

public class ControllerVue2Activity extends FragmentActivity implements OnMapReadyCallback, Observer, View.OnClickListener {

    private GoogleMap mMap;
    private ModeleVue2 modeleVue2;
    private LatLng init;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_vue2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mMap = mapFragment.getMap();
        modeleVue2 = new ModeleVue2();
        modeleVue2.addObserver(this);
        init = new LatLng(46.155, -1.155);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // anoune a modifier quelques choses
        LatLng minimes = new LatLng(46.155, -1.155);
        mMap.addMarker(new MarkerOptions().position(minimes).title("Marker in port des Minimes"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(minimes,16));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                modeleVue2.setModeleVue2(latLng);
            }
        });
    }

    @Override
    public void update(Observable observable, Object data) {
        /*methode qui d�finie la modification de la vue par le modele*/
        if(observable instanceof ModeleVue2){
           ModeleVue2  modele2 = (ModeleVue2)data;
           mMap.addPolyline(new PolylineOptions().geodesic(true)
           .add(init)
           .add(modele2.getLast().getWayPoint()));
            init = modele2.getLast().getWayPoint();
            mMap.addMarker(new MarkerOptions().position(init).title("Marker X"));
        }
    }

    @Override
    public void onClick(View v) {

    }
}
