package com.baldepro.balde.dronedriving;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class ControllerVue1Activity extends FragmentActivity implements OnMapReadyCallback, Observer {
    private GoogleMap mMap;
    private LatLng point;

    @Override
    public void update(Observable observable, Object data) {


    }

    public static  double  convertNmeaLongitude(String[] nmeaArray,String lon, String orientation) {
// .......
        lon = nmeaArray[3];
        orientation = nmeaArray[4];
        double longitude  = 0.0;
        if (lon != null && orientation != null ) {
            double temp1 = Double.parseDouble(lon);
            double temp2 = Math.floor(temp1/100);
            double temp3 = (temp1/100 - temp2)/0.6;
            if (orientation.equals("W")) {
                longitude = -(temp1+temp3);
            } else if (orientation.equals("E")) {
                longitude = (temp1+temp3);
            }
        }
        return longitude/100;

    }

    public double convertNmeaLatitude(String[] nmeaArray, String lat, String orientation) {
        //String[] nmeaArray = recupGLL(trame);
        lat = nmeaArray[1];
        orientation = nmeaArray[2];
        double longitude  = 0.0;
        if (lat != null && orientation != null ) {
            double temp1 = Double.parseDouble(lat);
            double temp2 = Math.floor(temp1/100);
            double temp3 = (temp1/100 - temp2)/0.6;
            if (orientation.equals("S")) {
                longitude = -(temp1+temp3);
            } else if (orientation.equals("N")) {
                longitude = (temp1+temp3);
            }
        }
        return longitude/100;

    }



    // Variable de recuperation des informations
    // private LinearLayout layout;
    // private TextView vue;
    //  private TextView v;
    private Socket socket;
    private BufferedReader in;
    private InputStreamReader ir;
    private String txt;
    private String long12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_controller_vue1);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        // layout = new LinearLayout(this);
        // vue = new TextView(this);
        //  v = new TextView(this);
        //  v.setText("");
        //  layout.setOrientation(LinearLayout.VERTICAL);
        //  layout.addView(vue);
        //  layout.addView(v);
        //  setContentView(layout);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                BufferedReader in;
                //FileWriter f=new FileWriter("file.txt");
                try {
                    socket = new Socket("172.16.2.66", 1234);

                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    // String message = in.readLine();
                    String ligne="";

                    while ((ligne = in.readLine()) != null) {
                        txt=txt+ligne+"\n";
                        if (ligne.startsWith("$GPGLL")){
                            txt=ligne+"cheikhna";

                            runOnUiThread(new Runnable() {
                                @Override

                                public void run() {

                                    if( txt!=null  ){

                                        String s=txt.substring(0, 45);
                                        String[] tab=s.split(",", 7);
                                        String lat="";
                                        String orien="";

                                        double latitude=convertNmeaLatitude(tab, lat, orien);
                                        // String aff=String.valueOf(latitude);
                                        double longitude=convertNmeaLongitude(tab, lat, orien);
                                        // String aff1=String.valueOf(longitude);
                                        // v.setText(aff);
                                        // vue.setText(aff1);

                                    }
                                }
                            } );
                        }



                    }
                    // f.close();
                } catch (UnknownHostException e) {
                    //  v.setText("non connecte");
                    e.printStackTrace();
                } catch (IOException e) {
                    // v.setText("non connecte");
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
