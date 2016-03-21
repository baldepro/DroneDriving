package com.baldepro.balde.dronedriving;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by balde on 21/03/2016.
 */
public class ModeleVue3 extends Observable{
    private LatLng positionInitiale;
    private LatLng positionCourante;
    private float puissanceMoteur1;
    private float puissanceMoteur2;
    private ArrayList<LatLng> listeWayPoint;

    public ModeleVue3(LatLng positionInitiale, float puissanceMoteur1, float puissanceMoteur2) {
        this.positionInitiale = positionInitiale;
        this.positionCourante = positionInitiale;
        this.puissanceMoteur1 = puissanceMoteur1;
        this.puissanceMoteur2 = puissanceMoteur2;
        this.listeWayPoint = new ArrayList<>();
    }

    public LatLng getPositionInitiale() {
        return positionInitiale;
    }

    public LatLng getPositionCourante() {
        return positionCourante;
    }

    public float getPuissanceMoteur1() {
        return puissanceMoteur1;
    }

    public float getPuissanceMoteur2() {
        return puissanceMoteur2;
    }

    public ArrayList<LatLng> getListeWayPoint() {
        return listeWayPoint;
    }

    public void setPositionCourante(LatLng positionCourante) {
        this.positionCourante = positionCourante;
    }

    public void setPuissanceMoteur1(float puissanceMoteur1) {
        this.puissanceMoteur1 = puissanceMoteur1;
    }

    public void setPuissanceMoteur2(float puissanceMoteur2) {
        this.puissanceMoteur2 = puissanceMoteur2;
    }
    public void setChangeModele(float x, float y, float z){
        //manipulation des données
        LatLng pos = new LatLng(z,x);
        positionCourante = pos;
        if(listeWayPoint.contains(pos)){
            puissanceMoteur1 = puissanceMoteur1*2;
            puissanceMoteur2 = puissanceMoteur2*2;
        }
        this.setChanged();
        this.notifyObservers(this);
    }
}
