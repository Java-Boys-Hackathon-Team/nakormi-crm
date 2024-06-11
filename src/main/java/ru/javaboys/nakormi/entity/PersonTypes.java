package ru.javaboys.nakormi.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum PersonTypes implements EnumClass<Integer> {

    VOLUNTEER(10),
    SUPERVISOR(20),
    BENEFICIARY(30);

    private final Integer id;

    PersonTypes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static PersonTypes fromId(Integer id) {
        for (PersonTypes at : PersonTypes.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}