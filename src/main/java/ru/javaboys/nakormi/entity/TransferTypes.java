package ru.javaboys.nakormi.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum TransferTypes implements EnumClass<String> {

    UNATTACHED_PICKUP("UPU"),
    PICKUP_FROM_POINT("PFP"),
    TRANSFER_TO_WAREHOUSE("TTW"),
    TRANSFER_FROM_WAREHOUSE("TFW"),
    TRANSFER_TO_VOLUNTEER("TTV"),
    FEED("FED"),
    TRANSFER_TO_BENEFICIARY("TTB"),
    UNATTACHED_WRITEOFF("UWO"),
    ACCEPTANCE_TO_WAREHOUSE("ATW"),
    SHIPMENT_FROM_WAREHOUSE("SFW")
    ;

    private final String id;

    TransferTypes(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static TransferTypes fromId(String id) {
        for (TransferTypes at : TransferTypes.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}