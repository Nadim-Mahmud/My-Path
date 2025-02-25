package com.wheelchair.mypath.service;

import com.graphhopper.ResponsePath;
import com.graphhopper.util.PointList;
import com.graphhopper.util.details.PathDetail;
import com.graphhopper.util.shapes.GHPoint3D;
import com.wheelchair.mypath.model.Coordinate;
import com.wheelchair.mypath.model.TurnDirection;
import com.wheelchair.mypath.model.apiresponse.*;
import com.wheelchair.mypath.utils.GeoUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.wheelchair.mypath.constants.Constants.*;
import static com.wheelchair.mypath.model.PathDetails.SURFACE;
import static com.wheelchair.mypath.model.TurnDirection.STRAIGHT;
import static com.wheelchair.mypath.utils.GeoUtils.*;
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

        processStartEnd(pointList);
        processDistance(pointList);
        processDuration(pointList);


        Route route = new Route();
        route.setPoints(pointList);

        Response response = new Response();
        response.setRoutes(route);

        return response;
    }

    private List<Point> getPointListGroupedBySurface(List<Coordinate> coordinateList, Map<String, List<PathDetail>> pathDetails) {
        List<PathDetail> surfaceList = pathDetails.get(SURFACE.getLabel());
        List<Point> pointList = new ArrayList<>();

        for (PathDetail pathDetail: surfaceList) {
            Point point = new Point();

            point.setSurface((String) pathDetail.getValue());
            point.setPoints(getSubArray(coordinateList, pathDetail.getFirst(), pathDetail.getLast()));

            pointList.add(point);
        }

        return pointList;
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

                    TurnDirection turnDirection = getTurnDirection(start, mid, end);

                    // taking a turn
                    if (turnDirection == STRAIGHT ) {
                        newPoint.addCoordinate(end);
                    } else {
                        newPoint.setManeuver(turnDirection.getLabel());

                        newPoint = createNewSegment(point, mid, end);
                        points.add(newPoint);
                    }
                }
            }
        }

        return points;
    }

    private void processStartEnd(List<Point> points) {
        for (Point point : points) {
            List<Coordinate> coordinateList = point.getPoints();
            point.setStart_location(coordinateList.get(0));
            point.setEnd_location(coordinateList.get(coordinateList.size() - 1));
        }
    }

    private void processDistance(List<Point> points) {
        Double distance = 0.0;

        for (Point point : points) {
            distance = 0.0;
            List<Coordinate> coordinateList = point.getPoints();

            for (int i = 1; i < coordinateList.size(); i++) {
                distance += calcDistance(coordinateList.get(i - 1), coordinateList.get(i));
            }

            double distanceInMeter = distance * 1000;
            double distanceInMi = distance * 0.621371;

            Distance segmentDistance = new Distance();
            segmentDistance.setValue(distanceInMeter);
            segmentDistance.setType("meter");
            segmentDistance.setText(String.format("%.2f", distanceInMi) + " mi");

            point.setDistance(segmentDistance);
        }
    }

    private void processDuration(List<Point> points) {
        for (Point point : points) {
            double timeInSecond = (point.getDistance().getValue() * 3600.0) / (1609.34 * AVG_SPEED);
            double timeInMinue = timeInSecond / 60;

            Duration duration = new Duration();
            duration.setValue(timeInSecond);
            duration.setType("second");
            duration.setText(String.format("%.2f", timeInMinue) + " min");

            point.setDuration(duration);
        }
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
