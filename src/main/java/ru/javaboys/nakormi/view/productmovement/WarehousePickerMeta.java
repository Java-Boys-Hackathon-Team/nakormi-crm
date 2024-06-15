package ru.javaboys.nakormi.view.productmovement;

import ru.javaboys.nakormi.entity.Warehouse;
import ru.javaboys.nakormi.entity.WarehouseTypes;

public class WarehousePickerMeta extends FieldMeta<Warehouse> {
    private final WarehouseTypes type;

    public WarehousePickerMeta(boolean enable, Warehouse value, WarehouseTypes type) {
        super(enable, value);
        this.type = type;
    }

    public WarehouseTypes getType() {
        return type;
    }

    public static WarehousePickerMeta enabled() {
        return new WarehousePickerMeta(false, null, null);
    }

    public static WarehousePickerMeta disabled() {
        return new WarehousePickerMeta(false, null, null);
    }

    public static WarehousePickerMeta enabled(WarehouseTypes type) {
        return new WarehousePickerMeta(true, null, type);
    }

    public static WarehousePickerMeta disabled(Warehouse warehouse) {
        return new WarehousePickerMeta(false, warehouse, null);
    }
}