package ru.javaboys.nakormi.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum MovementTypes implements EnumClass<String> {

    INCOME("I"),
    OUTCOME("O");

    private final String id;

    MovementTypes(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static MovementTypes fromId(String id) {
        for (MovementTypes at : MovementTypes.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}