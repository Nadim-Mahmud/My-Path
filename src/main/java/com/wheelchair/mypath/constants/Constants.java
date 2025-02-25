package com.wheelchair.mypath.constants;

/**
 * @author Nadim Mahmud
 * @date 2/21/25
 */
public interface Constants {
    //Paths
    String PBF_PATH = "src/main/resources/geofabrik/ohio-latest.osm.pbf";
    String GH_CACHE_PATH = "gh-cache/routing-graph-cache";
    String WHEELCHAIR_CUSTOM_MODEL_PATH = "src/main/resources/custom-models/wheelchair.json";

    String TURN_RIGHT = "Turn right";
    String TURN_LEFT = "Turn left";

    Double MAX_ANGLE_TO_TURN = 150.0;
    Double DEGREE_180 = 180.0;
    Double DEGREE_360 = 360.0;
    Double ALLOWED_STRAIGHT_ANGLE = 13.0;

    Double AVG_SPEED = 5.0;
}
