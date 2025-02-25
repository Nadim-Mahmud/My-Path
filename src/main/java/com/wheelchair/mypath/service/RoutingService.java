package com.wheelchair.mypath.service;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.wheelchair.mypath.exceptions.RouteNotFound;
import com.wheelchair.mypath.model.CustomProfiles;
import com.wheelchair.mypath.model.apiresponse.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.wheelchair.mypath.model.CustomProfiles.WHEEL_CHAIR;
import static com.wheelchair.mypath.model.PathDetails.*;

/**
 * @author Nadim Mahmud
 * @date 2/21/25
 */
@Service
public class RoutingService {

    @Autowired
    private GraphHopper graphHopper;

    @Autowired
    private NavigationService navigationService;

    public Response getBestRoute(double fromLat, double fromLon, double toLat, double toLon) {
        GHResponse ghResponse = getGPHRoute(fromLat, fromLon, toLat, toLon);

        if (ghResponse.hasErrors()) {
            throw new RouteNotFound("Route not found!");
        }

        return navigationService.getNavigation(ghResponse.getBest());
    }

    public GHResponse getGPHRoute(double fromLat, double fromLon, double toLat, double toLon) {
        List<String> details = new ArrayList();
        details.add(SURFACE.getLabel());
        details.add(STREET_NAME.getLabel());
        details.add(FOOT_WAY.getLabel());

        GHRequest request = new GHRequest(fromLat, fromLon, toLat, toLon)
                .setProfile(WHEEL_CHAIR.getLabel())
                .setPathDetails(details);

        return graphHopper.route(request);
    }
}
