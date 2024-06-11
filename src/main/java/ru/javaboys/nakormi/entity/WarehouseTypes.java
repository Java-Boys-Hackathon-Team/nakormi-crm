package ru.javaboys.nakormi.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum WarehouseTypes implements EnumClass<Integer> {

    PICKUP_POINT(10),
    STORAGE(20),
    PERSONAL(30);

    private final Integer id;

    WarehouseTypes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static WarehouseTypes fromId(Integer id) {
        for (WarehouseTypes at : WarehouseTypes.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}