package ru.javaboys.nakormi.dto;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.JmixId;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import ru.javaboys.nakormi.entity.Attachment;
import ru.javaboys.nakormi.entity.Person;
import ru.javaboys.nakormi.entity.TransferTypes;
import ru.javaboys.nakormi.entity.Volunteer;
import ru.javaboys.nakormi.entity.Warehouse;

import java.util.List;
import java.util.UUID;

import static ru.javaboys.nakormi.validation.FoodTransferTypes.*;

@JmixEntity
public class ProductMovement {
    @JmixGeneratedValue
    @JmixId
    private UUID id;

    @JmixProperty(mandatory = true)
    @NotNull(message = "Тип перемещения обязателен для заполнения")
    private String transferType;

    @NotNull(
            message = "Склад-источник обязателен для заполнения",
            groups = {PickupFromPoint.class, TransferToWarehouse.class,
                    TransferFromWarehouse.class, TransferToVolunteer.class,
                    Feed.class, TransferToBeneficiary.class, UnattachedWriteoff.class}
    )
    @Null(
            message = "Склад-источник должен быть не заполнен",
            groups = {UnattachedPickup.class}
    )
    private Warehouse warehouseSource;

    @NotNull(
            message = "Склад-приемник обязателен для заполнения",
            groups = {UnattachedPickup.class, PickupFromPoint.class,
                    TransferToWarehouse.class, TransferFromWarehouse.class,
                    TransferToVolunteer.class}
    )
    @Null(
            message = "Склад-приемник должен быть не заполнен",
            groups = {Feed.class, TransferToBeneficiary.class, UnattachedWriteoff.class}
    )
    private Warehouse warehouseReceiver;

    @NotNull(
            message = "Благополучатель обязателен для заполнения",
            groups = {TransferToBeneficiary.class}
    )
    @Null(
            message = "Благополучатель должен быть не заполнен",
            groups = {UnattachedPickup.class, PickupFromPoint.class,
                    TransferToWarehouse.class, TransferFromWarehouse.class,
                    TransferToVolunteer.class, Feed.class, UnattachedWriteoff.class}
    )
    private Person beneficiary;

    @JmixProperty(mandatory = true)
    @NotNull(message = "Волонтер обязателен для заполнения")
    private Volunteer volunteer;

    @Valid
    @NotEmpty(message = "Детали перемещения обязательны для заполнения")
    private List<ProductMovementRow> details;

    @Valid
    @NotEmpty(message = "Вложения обязательны для заполнения")
    private List<Attachment> attachments;

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Person getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Person beneficiary) {
        this.beneficiary = beneficiary;
    }

    public @NotNull(message = "Волонтер обязателен для заполнения") Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(@NotNull(message = "Волонтер обязателен для заполнения") Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public List<ProductMovementRow> getDetails() {
        return details;
    }

    public void setDetails(List<ProductMovementRow> details) {
        this.details = details;
    }

    public void setWarehouseReceiver(Warehouse warehouseReceiver) {
        this.warehouseReceiver = warehouseReceiver;
    }

    public Warehouse getWarehouseReceiver() {
        return warehouseReceiver;
    }

    public void setWarehouseSource(Warehouse warehouseSource) {
        this.warehouseSource = warehouseSource;
    }

    public Warehouse getWarehouseSource() {
        return warehouseSource;
    }

    public void setTransferType(TransferTypes transferType) {
        this.transferType = transferType == null ? null : transferType.getId();
    }

    public TransferTypes getTransferType() {
        return transferType == null ? null : TransferTypes.fromId(transferType);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}