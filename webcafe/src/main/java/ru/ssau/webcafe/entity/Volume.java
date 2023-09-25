package ru.ssau.webcafe.entity;
public record Volume(int value, VolumeType type) {
    @Override
    public String toString() {
        return "%s %s".formatted(value, type.getTypeName());
    }
}