package ru.javaboys.nakormi.entity;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.springframework.lang.Nullable;

public enum PersonGenderType implements EnumClass<String> {

    MALE("M"),
    FEMALE("F");

    private final String id;

    PersonGenderType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PersonGenderType fromId(String id) {
        for (PersonGenderType at : PersonGenderType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}