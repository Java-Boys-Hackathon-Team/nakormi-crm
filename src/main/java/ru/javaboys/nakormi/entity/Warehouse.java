package ru.javaboys.nakormi.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

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

    @InstanceName
    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    @Column(name = "CONTACTS")
    private String contacts;

    @Column(name = "STORAGE_TYPE")
    private Integer storage_type;

    @JoinColumn(name = "SUPERVISOR_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Person supervisor;

    public Person getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Person supervisor) {
        this.supervisor = supervisor;
    }

    public WarehouseTypes getStorage_type() {
        return storage_type == null ? null : WarehouseTypes.fromId(storage_type);
    }

    public void setStorage_type(WarehouseTypes storage_type) {
        this.storage_type = storage_type == null ? null : storage_type.getId();
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