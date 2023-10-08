package ru.ssau.webcaffe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.Arrays;

public record Weight(
        @Column(name = "weight") int value,
        @Column(name = "weight_type") @Enumerated(EnumType.STRING) WeightType type
) {
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