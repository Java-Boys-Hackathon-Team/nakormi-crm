package ru.javaboys.nakormi.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "FOOD_TRANSFER", indexes = {
        @Index(name = "IDX_FOOD_TRANSFER_WAREHOUSE_SOURCE", columnList = "WAREHOUSE_SOURCE_ID"),
        @Index(name = "IDX_FOOD_TRANSFER_WAREHOUSE_RECEIVER", columnList = "WAREHOUSE_RECEIVER_ID"),
        @Index(name = "IDX_FOOD_TRANSFER_VOLUNTEER", columnList = "VOLUNTEER_ID")
})
@SequenceGenerator(name = "foodTransferNumberSequence", initialValue = 1, allocationSize = 1)
@Entity
public class FoodTransfer {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JmixGeneratedValue(sequenceName = "foodTransferNumberSequence")
    @Column(name = "NUMBER_", nullable = false, updatable = false)
    @NotNull
    private Integer number;

    @Column(name = "DATE_")
    private LocalDateTime date;

    @InstanceName
    @Transient
    @JmixProperty
    private String numberFormatted;

    @JoinColumn(name = "VOLUNTEER_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Volunteer volunteer;

    @Column(name = "TRANSFER_TYPE")
    private String transferType;

    @JoinColumn(name = "WAREHOUSE_SOURCE_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Warehouse warehouseSource;

    @JoinColumn(name = "WAREHOUSE_RECEIVER_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Warehouse warehouseReceiver;

    @OneToMany(mappedBy = "foodTransfer", cascade = CascadeType.ALL)
    private List<FoodTransferRow> rows;

    @ManyToMany
    @JoinTable(
            name = "FOOD_TRANSFER_ATTACHMENT", // Имя связующей таблицы
            joinColumns = @JoinColumn(name = "FOOD_TRANSFER_ID", referencedColumnName = "ID"), // Колонка в связующей таблице для FOOD_TRANSFER
            inverseJoinColumns = @JoinColumn(name = "ATTACHMENT_ID", referencedColumnName = "ID") // Колонка в связующей таблице для ATTACHMENT
    )
    private List<Attachment> attachments;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<FoodTransferRow> getRows() {
        return rows;
    }

    public void setRows(List<FoodTransferRow> rows) {
        this.rows = rows;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Warehouse getWarehouseReceiver() {
        return warehouseReceiver;
    }

    public void setWarehouseReceiver(Warehouse warehouse_receiver) {
        this.warehouseReceiver = warehouse_receiver;
    }

    public Warehouse getWarehouseSource() {
        return warehouseSource;
    }

    public void setWarehouseSource(Warehouse warehouse_source) {
        this.warehouseSource = warehouse_source;
    }

    public TransferTypes getTransferType() {
        return transferType == null ? null : TransferTypes.fromId(transferType);
    }

    public void setTransferType(TransferTypes transfer_type) {
        this.transferType = transfer_type == null ? null : transfer_type.getId();
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumberFormatted() {
        return numberFormatted;
    }

    public void setNumberFormatted(String numberFormatted) {
        this.numberFormatted = numberFormatted;
    }

    @PostLoad
    public void postLoad() {
        this.numberFormatted = String.format("T%03d", number);
    }

}