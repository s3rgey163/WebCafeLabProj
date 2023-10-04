package ru.ssau.webcaffe.entity;

import lombok.Getter;

public enum WeightType {
    ML("мл."),
    L("л."),
    G("г."),
    KG("кг."),
    MG("мг.")
    ;

    @Getter
    private String typeName;

    WeightType(String typeName) {
        this.typeName = typeName;
    }
}
