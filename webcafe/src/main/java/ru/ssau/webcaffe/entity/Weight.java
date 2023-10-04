package ru.ssau.webcaffe.entity;

import java.util.Arrays;

public record Weight(int value, WeightType type) {
    public Weight {
        if(type == null) throw new IllegalArgumentException(
                "Type must be on of: " + Arrays.toString(WeightType.values())
        );
    }
    @Override
    public String toString() {
        return "%s %s".formatted(value, type.getTypeName());
    }
}