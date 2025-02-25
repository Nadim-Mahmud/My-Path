package com.wheelchair.mypath.service;

import com.graphhopper.ResponsePath;
import com.graphhopper.util.PointList;
import com.graphhopper.util.details.PathDetail;
import com.graphhopper.util.shapes.GHPoint3D;
import com.wheelchair.mypath.model.Coordinate;
import com.wheelchair.mypath.model.apiresponse.Point;
import com.wheelchair.mypath.model.apiresponse.Response;
import com.wheelchair.mypath.model.apiresponse.Route;
import com.wheelchair.mypath.utils.GeoUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.wheelchair.mypath.constants.Constants.ALLOWED_STRAIGHT_ANGLE;
import static com.wheelchair.mypath.constants.Constants.DEGREE_360;
import static com.wheelchair.mypath.model.PathDetails.SURFACE;
import static com.wheelchair.mypath.utils.Utils.getSubArray;

/**
 * @author Nadim Mahmud
 * @date 2/24/25
 */
@Service
public class NavigationService {

    public Response getNavigation(ResponsePath responsePath) {
        return generateResponse(responsePath);
    }

    private Response generateResponse(ResponsePath responsePath) {
        List<Coordinate> coordinateList = getRouteCoordinates(responsePath);
        Map<String, List<PathDetail>> pathDetails = responsePath.getPathDetails();

        List<Point> pointList = getPointListGroupedBySurface(coordinateList, pathDetails);
        pointList = processManeuverSegments(pointList);

        Route route = new Route();
        route.setPoints(pointList);

        Response response = new Response();
        response.setRoutes(route);

        return response;
    }

    private List<Point> processManeuverSegments(List<Point> pointList) {
        List<Point> points = new ArrayList<>();

        for (Point point : pointList) {
            if (point.getPoints().size() <= 2) {
                points.add(point);
            } else {
                List<Coordinate> coordinateList = point.getPoints();
                Point newPoint = createNewSegment(point, coordinateList.get(0), coordinateList.get(1));
                points.add(newPoint);

                for (int i = 2; i < coordinateList.size(); i++) {
                    Coordinate start = coordinateList.get(i - 2);
                    Coordinate mid = coordinateList.get(i - 1);
                    Coordinate end = coordinateList.get(i);

                    double headingChange = GeoUtils.calculateHeadingChange(start, mid, end);

                    // taking a turn
                    if (headingChange > ALLOWED_STRAIGHT_ANGLE && headingChange < (DEGREE_360 - ALLOWED_STRAIGHT_ANGLE)) {
                        newPoint = createNewSegment(point, mid, end);
                        points.add(newPoint);
                    } else {
                        newPoint.addCoordinate(end);
                    }
                }
            }
        }

        return points;
    }

    private List<Point> getPointListGroupedBySurface(List<Coordinate> coordinateList, Map<String, List<PathDetail>> pathDetails) {
        List<PathDetail> surfaceList = pathDetails.get(SURFACE.getLabel());
        List<Point> pointList = new ArrayList<>();

        for (PathDetail pathDetail: surfaceList) {
            Point point = new Point();

            point.setSurface((String) pathDetail.getValue());
            point.setStart_location(coordinateList.get(pathDetail.getFirst()));
            point.setEnd_location(coordinateList.get(pathDetail.getLast()));
            point.setPoints(getSubArray(coordinateList, pathDetail.getFirst(), pathDetail.getLast()));

            pointList.add(point);
        }

        return pointList;
    }

    private Point createNewSegment(Point point, Coordinate coo1, Coordinate coo2) {
        Point newPoint = point.cloneWithEmptyCoordinateList();
        newPoint.addCoordinate(coo1);
        newPoint.addCoordinate(coo2);

        return newPoint;
    }

    private List<Coordinate> getRouteCoordinates(ResponsePath responsePath) {
        PointList pointList = responsePath.getPoints();
        List<Coordinate> coordinateList = new ArrayList<>();

        for (int i = 0; i < pointList.size(); i++) {
            GHPoint3D ghPoint3D = pointList.get(i);
            coordinateList.add(new Coordinate(ghPoint3D.getLat(), ghPoint3D.getLon(), ghPoint3D.getEle()));
        }

        return coordinateList;
    }
}
