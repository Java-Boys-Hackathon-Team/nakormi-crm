package ru.javaboys.nakormi.entity;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.springframework.lang.Nullable;

public enum PersonTypes implements EnumClass<String> {

    VOLUNTEER("V"),
    SUPERVISOR("S"),
    BENEFICIARY("B");

    private final String id;

    PersonTypes(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PersonTypes fromId(String id) {
        for (PersonTypes at : PersonTypes.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}