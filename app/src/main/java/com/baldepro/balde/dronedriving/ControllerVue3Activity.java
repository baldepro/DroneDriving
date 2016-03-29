package com.baldepro.balde.dronedriving;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

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
    private Sensor magnetic;
    private LatLng PositionInit;
    private String mytext;
    double  ancienneVal=0;
    private TextView txt;
    private float [] accVector = new float[3];
    private float [] magVector = new float[3];
    private float [] resultMatrix = new float[9];
    private float [] valeurs = new float[3];
    private MarkerOptions marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_vue3);
        modeleVue3 = new ModeleVue3(new LatLng(46.155,-1.155),12,13);
        modeleVue3.addObserver(this);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acceleromtre = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        PositionInit = new LatLng(46.155, -1.155);
        mMap = mapFragment.getMap();
        marker = new MarkerOptions();
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this, acceleromtre);
        sensorManager.unregisterListener(this, magnetic);
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, acceleromtre, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this,magnetic, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.addMarker(new MarkerOptions().position(PositionInit).title("Les minimes à la Rochelle"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PositionInit, 2));
    }

    @Override
    public void update(Observable observable, Object data) {
        if(observable instanceof ModeleVue3) {
            //ModeleVue3 modele = (ModeleVue3)data;
            marker.position(PositionInit);
            mMap.addMarker(marker);
            txt.setText(mytext);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double x,y,z,X=0,Y=0,Z=0;
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accVector = event.values;
        }
        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magVector = event.values;
        }
        SensorManager.getRotationMatrix(resultMatrix, null, accVector, magVector);
        SensorManager.getOrientation(resultMatrix, valeurs);
        x = (float) Math.toDegrees(valeurs[0]);
        ancienneVal = PositionInit.longitude;
        X = x+PositionInit.longitude;
        if((ancienneVal - X > 20)){
            X = x+PositionInit.longitude;
            Y = PositionInit.latitude+0.01;
            LatLng point = new LatLng(Y,X);
            PositionInit = point;
            modeleVue3.setChangeModele(46,-1,15);
            mytext = String.valueOf(X)+ " , "+ String.valueOf(Y) +" , "+ String.valueOf(Z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
