package ru.javaboys.nakormi.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.UUID;

@JmixEntity
@Table(name = "VOLUNTEER", indexes = {
        @Index(name = "IDX_VOLUNTEER_PERSON", columnList = "PERSON_ID"),
        @Index(name = "IDX_VOLUNTEER_WAREHOUSE", columnList = "WAREHOUSE_ID")
})
@Entity
public class Volunteer {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @JoinColumn(name = "PERSON_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Person person;

    @Column(name = "PASSPORT_NUMBER", length = 30)
    private String passportNumber;

    @Column(name = "TELEGRAM_ID", length = 64)
    private String telegramId;

    @JoinColumn(name = "WAREHOUSE_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Warehouse warehouse;

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(String telegram_id) {
        this.telegramId = telegram_id;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passport_number) {
        this.passportNumber = passport_number;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}