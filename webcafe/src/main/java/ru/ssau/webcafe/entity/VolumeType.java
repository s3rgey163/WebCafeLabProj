package ru.ssau.webcafe.entity;

import lombok.Getter;

public enum VolumeType {
    ML("мл."),
    L("л."),
    G("г."),
    KG("кг."),
    ;

    @Getter
    private String typeName;

    VolumeType(String typeName) {
        this.typeName = typeName;
    }
}
