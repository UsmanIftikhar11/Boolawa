package com.example.hamziii.boolawa;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import com.example.hamziii.boolawa.Route;


public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
