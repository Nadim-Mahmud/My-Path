package com.wheelchair.mypath.constants;

/**
 * @author Nadim Mahmud
 * @date 2/21/25
 */
public interface Constants {
    //Paths
//    String PBF_PATH = "src/main/resources/geofabrik/maryland-latest.osm.pbf";
//    String PBF_PATH = "src/main/resources/geofabrik/ohio-latest.osm.pbf";
    String PBF_PATH = "src/main/resources/geofabrik/ohio-maryland.pbf";
    String GH_CACHE_PATH = "gh-cache/routing-graph-cache";
    String WHEELCHAIR_CUSTOM_MODEL_PATH = "src/main/resources/custom-models/wheelchair.json";

    Double DEGREE_180 = 180.0;
    Double DEGREE_360 = 360.0;

    Double STRAIGHT_THRESHOLD = 13.0;
    Double SLIGHT_THRESHOLD = 45.0;
    Double STEEP_THRESHOLD = 90.0;

    Double AVG_SPEED = 5.0;
}
