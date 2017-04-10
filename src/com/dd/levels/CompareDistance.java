package com.dd.levels;

/**
 * Created by meracoid on 4/9/17.
 */
public class CompareDistance {
    private int distance;
    private MapPosition position;

    public CompareDistance(int distance, MapPosition position) {
        this.distance = distance;
        this.position = position;
    }

    public CompareDistance() {

    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public MapPosition getPosition() {
        return position;
    }

    public void setPosition(MapPosition position) {
        this.position = position;
    }
}