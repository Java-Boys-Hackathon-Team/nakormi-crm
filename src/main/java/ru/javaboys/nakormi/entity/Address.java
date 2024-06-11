package ru.javaboys.nakormi.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JmixEntity
@Table(name = "ADDRESS", indexes = {
        @Index(name = "IDX_ADDRESS_DISTRICT", columnList = "DISTRICT_ID")
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

    @InstanceName
    @Column(name = "ADDRESS_TEXT", length = 1000)
    private String address_text;

    public String getAddress_text() {
        return address_text;
    }

    public void setAddress_text(String address_text) {
        this.address_text = address_text;
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