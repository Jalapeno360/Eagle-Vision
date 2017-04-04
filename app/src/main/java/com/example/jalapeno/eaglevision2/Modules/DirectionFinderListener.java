package com.example.jalapeno.eaglevision2.Modules;

import java.util.List;

/**
 * Created by jalapeno on 4/3/2017.
 */

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}