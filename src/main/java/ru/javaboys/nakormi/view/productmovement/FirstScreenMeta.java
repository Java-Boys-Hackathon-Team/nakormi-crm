package ru.javaboys.nakormi.view.productmovement;

import ru.javaboys.nakormi.entity.Volunteer;

public class FirstScreenMeta {
    private final FieldMeta<Volunteer> volunteerSourceMeta;
    private final FieldMeta<Volunteer> volunteerReceiverMeta;
    private final WarehousePickerMeta warehousesSourceMeta;
    private final WarehousePickerMeta warehousesReceiverMeta;
    private final PersonPickerMeta personMeta;

    public FirstScreenMeta(FieldMeta<Volunteer> volunteerSourceMeta,
                           FieldMeta<Volunteer> volunteerReceiverMeta,
                           WarehousePickerMeta warehousesSourceMeta,
                           WarehousePickerMeta warehousesReceiverMeta,
                           PersonPickerMeta personMeta) {
        this.volunteerSourceMeta = volunteerSourceMeta;
        this.volunteerReceiverMeta = volunteerReceiverMeta;
        this.warehousesSourceMeta = warehousesSourceMeta;
        this.warehousesReceiverMeta = warehousesReceiverMeta;
        this.personMeta = personMeta;
    }

    public FieldMeta<Volunteer> getVolunteerSourceMeta() {
        return volunteerSourceMeta;
    }

    public FieldMeta<Volunteer> getVolunteerReceiverMeta() {
        return volunteerReceiverMeta;
    }

    public WarehousePickerMeta getWarehousesSourceMeta() {
        return warehousesSourceMeta;
    }

    public WarehousePickerMeta getWarehousesReceiverMeta() {
        return warehousesReceiverMeta;
    }

    public PersonPickerMeta getBeneficiaryPersonMeta() {
        return personMeta;
    }

    public static FirstScreenMeta disabled() {
        return new FirstScreenMeta(
                FieldMeta.disabled(),
                FieldMeta.disabled(),
                WarehousePickerMeta.disabled(),
                WarehousePickerMeta.disabled(),
                PersonPickerMeta.disabled()
        );
    }
}