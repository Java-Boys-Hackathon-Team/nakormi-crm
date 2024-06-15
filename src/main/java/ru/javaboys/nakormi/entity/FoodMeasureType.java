package ru.javaboys.nakormi.entity;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.springframework.lang.Nullable;

public enum FoodMeasureType implements EnumClass<String> {

    WEIGHT("W"),
    PIECE("P");

    private final String id;

    FoodMeasureType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static FoodMeasureType fromId(String id) {
        for (FoodMeasureType at : FoodMeasureType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}