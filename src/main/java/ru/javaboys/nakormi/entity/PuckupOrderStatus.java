package ru.javaboys.nakormi.entity;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.springframework.lang.Nullable;


public enum PuckupOrderStatus implements EnumClass<String> {

    CREATED("C"),
    REJECTED("R"),
    EXECUTED("E");

    private final String id;

    PuckupOrderStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PuckupOrderStatus fromId(String id) {
        for (PuckupOrderStatus at : PuckupOrderStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}