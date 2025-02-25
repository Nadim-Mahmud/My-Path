package com.wheelchair.mypath.model;

/**
 * @author Nadim Mahmud
 * @date 2/24/25
 */
public enum TurnDirection {
    LEFT("Turn left"),
    RIGHT("Trun right"),
    STRAIGHT("Go straight");

    private final String label;

    TurnDirection(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
