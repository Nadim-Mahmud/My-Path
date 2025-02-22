package com.wheelchair.mypath.service;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Nadim Mahmud
 * @date 2/21/25
 */
@Service
public class RoutingService {

    @Autowired
    private GraphHopper graphHopper;

    public GHResponse calculateRoute(double fromLat, double fromLon, double toLat, double toLon) {
        GHRequest request = new GHRequest(fromLat, fromLon, toLat, toLon)
                .setProfile("car"); // Use the "car" profile defined earlier
        return graphHopper.route(request);
    }
}
