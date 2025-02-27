package com.wheelchair.mypath.model;

/**
 * @author Nadim Mahmud
 * @date 2/24/25
 */
public enum TurnDirection {
    LEFT("Turn left"),
    SLIGHT_LEFT("Turn slightly left"),
    RIGHT("Turn right"),
    SLIGHT_RIGHT("Turn slightly right"),
    STRAIGHT("Go straight"),
    END("Reached");

    private final String label;

    TurnDirection(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
