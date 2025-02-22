package com.wheelchair.mypath.model;

import java.io.Serializable;

/**
 * @author Nadim Mahmud
 * @date 10/19/24
 */
public class Coordinate {
    double latitude;
    double longitude;

    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
