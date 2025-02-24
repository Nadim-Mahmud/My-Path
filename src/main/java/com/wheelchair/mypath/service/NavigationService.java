package com.wheelchair.mypath.service;

import com.graphhopper.ResponsePath;
import com.graphhopper.util.PointList;
import com.graphhopper.util.shapes.GHPoint3D;
import com.wheelchair.mypath.model.Coordinate;
import com.wheelchair.mypath.model.apiresponse.Response;
import com.wheelchair.mypath.model.apiresponse.Route;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadim Mahmud
 * @date 2/24/25
 */
@Service
public class NavigationService {

    public Response getNavigation(ResponsePath responsePath) {
        Response response = new Response();
        Route route = new Route();
        response.setRoutes(route);

        List<Coordinate> coordinateList = getRouteCoordinates(responsePath);


        return response;
    }

    private List<Coordinate> getRouteCoordinates(ResponsePath responsePath) {
        PointList pointList = responsePath.getPoints();
        List<Coordinate> coordinateList = new ArrayList<>();

        for (int i = 0; i < pointList.size(); i++) {
            GHPoint3D ghPoint3D = pointList.get(i);
            coordinateList.add(new Coordinate(ghPoint3D.getLat(), ghPoint3D.getLon()));
        }

        return coordinateList;
    }
}
