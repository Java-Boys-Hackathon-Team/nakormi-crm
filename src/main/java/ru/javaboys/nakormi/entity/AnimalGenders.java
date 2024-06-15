package ru.javaboys.nakormi.entity;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.springframework.lang.Nullable;


public enum AnimalGenders implements EnumClass<String> {

    MALE("M"),
    FEMALE("F");

    private final String id;

    AnimalGenders(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static AnimalGenders fromId(String id) {
        for (AnimalGenders at : AnimalGenders.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}