package com.baldepro.balde.dronedriving;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Observable;
import java.util.Observer;

public class ControllerVue3Activity extends FragmentActivity implements OnMapReadyCallback, SensorEventListener, Observer {

    private GoogleMap mMap;
    private ModeleVue3 modeleVue3;
    private SensorManager sensorManager;
    private Sensor acceleromtre;
    private LatLng PositionInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_vue3);
        modeleVue3 = new ModeleVue3(new LatLng(46.155,-1.155),12,13);
        modeleVue3.addObserver(this);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acceleromtre = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        PositionInit = new LatLng(46.155, -1.155);
        mMap = mapFragment.getMap();
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this, acceleromtre);
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, acceleromtre,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        //LatLng minimes = new LatLng(46.155, -1.155);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.addMarker(new MarkerOptions().position(PositionInit).title("Les minimes à la Rochelle"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PositionInit, 17));
    }

    @Override
    public void update(Observable observable, Object data) {
        if(observable instanceof ModeleVue3) {
            ModeleVue3 modele = (ModeleVue3)data;
            mMap.addPolyline(new PolylineOptions().geodesic(true)
                            .add(PositionInit)
                            .add(modele.getPositionCourante())
                    );
            PositionInit = modele.getPositionCourante();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x,y,z;
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            modeleVue3.setChangeModele(x,y,z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
