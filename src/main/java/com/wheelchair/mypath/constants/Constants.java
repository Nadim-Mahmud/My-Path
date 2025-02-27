package com.wheelchair.mypath.constants;

/**
 * @author Nadim Mahmud
 * @date 2/21/25
 */
public interface Constants {
    String PBF_URL = "https://download.geofabrik.de/north-america/us/ohio-latest.osm.pbf";

    String DEFAULT_PBF_PATH = "src/main/resources/geofabrik/ohio-maryland.pbf";
    String PBF_PATH = "myPathDataStore/map.pbf";
    String GH_CACHE_PATH = "myPathDataStore/routing-graph-cache";
    String GH_TMP_CACHE_PATH = "myPathDataStore/tmp-routing-graph-cache";
    String WHEELCHAIR_CUSTOM_MODEL_PATH = "src/main/resources/custom-models/wheelchair.json";

    Double DEGREE_180 = 180.0;
    Double DEGREE_360 = 360.0;

    Double STRAIGHT_THRESHOLD = 13.0;
    Double SLIGHT_THRESHOLD = 45.0;
    Double STEEP_THRESHOLD = 90.0;

    Double AVG_SPEED = 5.0;
}
