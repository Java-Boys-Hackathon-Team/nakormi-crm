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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JmixEntity
@Table(name = "WAREHOUSE", indexes = {
        @Index(name = "IDX_WAREHOUSE_ADDRESS", columnList = "ADDRESS_ID"),
        @Index(name = "IDX_WAREHOUSE_SUPERVISOR", columnList = "SUPERVISOR_ID")
})
@Entity
public class Warehouse {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "ADDRESS_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Address address;

    @NotNull
    @InstanceName
    @Column(name = "DESCRIPTION", nullable = false, length = 1000)
    private String description;

    @Column(name = "CONTACTS")
    private String contacts;

    @Column(name = "STORAGE_TYPE", nullable = false)
    private String storageType;

    @JoinColumn(name = "SUPERVISOR_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Person supervisor;

    public Person getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Person supervisor) {
        this.supervisor = supervisor;
    }

    public WarehouseTypes getStorageType() {
        return storageType == null ? null : WarehouseTypes.fromId(storageType);
    }

    public void setStorageType(WarehouseTypes storage_type) {
        this.storageType = storage_type == null ? null : storage_type.getId();
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}