package ru.javaboys.nakormi.validation;

public class FoodTransferTypes {
    public interface TransferType {}

    public interface UnattachedPickup extends TransferType {}
    public interface PickupFromPoint extends TransferType {}
    public interface TransferToWarehouse extends TransferType {}
    public interface TransferFromWarehouse extends TransferType {}
    public interface TransferToVolunteer extends TransferType {}
    public interface Feed extends TransferType {}
    public interface TransferToBeneficiary extends TransferType {}
    public interface UnattachedWriteoff extends TransferType {}

}