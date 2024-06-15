package ru.javaboys.nakormi.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@JmixEntity
@Table(name = "ADDRESS", indexes = {
        @Index(name = "IDX_ADDRESS_DISTRICT", columnList = "DISTRICT_ID"),
        @Index(name = "IDX_ADDRESS_WAREHOUSE", columnList = "WAREHOUSE_ID")
})
@Entity
public class Address {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull(message = "Округ обязателен для заполнения")
    @JoinColumn(name = "DISTRICT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private District district;

    @NotNull
    @InstanceName
    @Column(name = "ADDRESS_TEXT", nullable = false, length = 1000)
    private String addressText;

    @NotNull
    @Column(name = "COORDINATE")
    private Point coordinate;

    @JoinColumn(name = "WAREHOUSE_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Warehouse warehouse;

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    public String getAddressText() {
        return addressText;
    }

    public void setAddressText(String address_text) {
        this.addressText = address_text;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}