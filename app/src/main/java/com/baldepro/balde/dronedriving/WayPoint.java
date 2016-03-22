package com.baldepro.balde.dronedriving;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Aymeric on 22/03/2016.
 */
public class WayPoint {
    private LatLng wayPoint;
    private float vitesse;

    public WayPoint(LatLng wayPoint, float vitesse) {
        this.wayPoint = wayPoint;
        this.vitesse = vitesse;
    }

    public LatLng getWayPoint() {
        return wayPoint;
    }

    public float getVitesse() {
        return vitesse;
    }

    public void setWayPoint(LatLng wayPoint) {
        this.wayPoint = wayPoint;
    }

    public void setVitesse(float vitesse) {
        this.vitesse = vitesse;
    }


}
