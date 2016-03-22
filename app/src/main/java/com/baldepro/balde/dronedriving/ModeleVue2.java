package com.baldepro.balde.dronedriving;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Observable;

public class ModeleVue2 extends Observable {
    private ArrayList <WayPoint> listWayPoints;
    private WayPoint last;

    public ArrayList <WayPoint> getListWayPoints() {
        return listWayPoints;
    }

    public void setListWayPoints(ArrayList <WayPoint> listWayPoints) {
        this.listWayPoints = listWayPoints;
    }

    public ModeleVue2() {
        this.listWayPoints = new ArrayList<>();
    }

    public WayPoint getLast() {
        return last;
    }

    public void setModeleVue2(LatLng wp){
        WayPoint wpt= new WayPoint(wp,15);
        listWayPoints.add(wpt);
        this.last = wpt;
        setChanged();
        notifyObservers(this);
    }
}
