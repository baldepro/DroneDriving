package com.baldepro.balde.dronedriving;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by cheikhna on 28/03/2016.
 */
public class ModeleVue1 extends Observable{
    private ArrayList<LatLng> listePoint;

    public ModeleVue1() {
        this.listePoint = new ArrayList<>();
    }

    public ArrayList<LatLng> getListePoint() {
        return listePoint;
    }

    public void setModele(LatLng point){
        this.listePoint.add(point);
        setChanged();
        notifyObservers(this);
    }
}
