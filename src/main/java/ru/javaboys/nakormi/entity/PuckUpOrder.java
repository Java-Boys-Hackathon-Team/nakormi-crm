package ru.javaboys.nakormi.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "PUCK_UP_ORDER", indexes = {
        @Index(name = "IDX_PUCK_UP_ORDER_CREATOR", columnList = "CREATOR_ID"),
        @Index(name = "IDX_PUCK_UP_ORDER_WAREHOUSE", columnList = "WAREHOUSE_ID"),
        @Index(name = "IDX_PUCK_UP_ORDER_VOLUNTEER", columnList = "VOLUNTEER_ID")
})
@Entity
public class PuckUpOrder {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "DATE_")
    private LocalDateTime date;

    @JmixGeneratedValue(sequenceName = "puckUpOrderNumberSequence")
    @Column(name = "NUMBER_", nullable = false, updatable = false)
    @NotNull
    private Integer number;

    @InstanceName
    @Transient
    @JmixProperty
    private String numberFormatted;

    @JoinColumn(name = "CREATOR_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Person creator;

    @JoinColumn(name = "VOLUNTEER_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Volunteer volunteer;

    @JoinColumn(name = "WAREHOUSE_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Warehouse warehouse;

    @Column(name = "IS_CLOSED")
    private Boolean isClosed;

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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
        this.numberFormatted = String.format("P%03d", number);
    }

}