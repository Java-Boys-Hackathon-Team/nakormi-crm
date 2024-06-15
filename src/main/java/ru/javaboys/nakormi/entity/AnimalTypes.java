package ru.javaboys.nakormi.entity;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.springframework.lang.Nullable;


public enum AnimalTypes implements EnumClass<String> {

    CAT("C"),
    DOG("D");

    private final String id;

    AnimalTypes(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static AnimalTypes fromId(String id) {
        for (AnimalTypes at : AnimalTypes.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}