package ru.ssau.webcafe.entity;

public enum VolumeType {
    ML("Миллилитры"),
    L("Литры"),
    G("Граммы"),
    KG("Килограммы"),
    ;

    private String name;

    VolumeType(String name) {
        this.name = name;
    }
}
