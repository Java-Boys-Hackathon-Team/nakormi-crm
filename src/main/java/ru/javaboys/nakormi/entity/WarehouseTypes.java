package ru.javaboys.nakormi.entity;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.springframework.lang.Nullable;

public enum WarehouseTypes implements EnumClass<String> {

    PICKUP_POINT("PP"),
    STORAGE("S"),
    PERSONAL("P");

    private final String id;

    WarehouseTypes(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static WarehouseTypes fromId(String id) {
        for (WarehouseTypes at : WarehouseTypes.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}