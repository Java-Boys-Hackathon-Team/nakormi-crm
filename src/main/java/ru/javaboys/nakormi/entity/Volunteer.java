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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "VOLUNTEER", indexes = {
        @Index(name = "IDX_VOLUNTEER_PERSON", columnList = "PERSON_ID"),
        @Index(name = "IDX_VOLUNTEER_WAREHOUSE", columnList = "WAREHOUSE_ID"),
        @Index(name = "IDX_VOLUNTEER_TELEGRAM_USER", columnList = "TELEGRAM_USER_ID")
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

    @Column(name = "PASSPORT_NUMBER")
    private String passportNumber;

    @JoinColumn(name = "WAREHOUSE_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Warehouse warehouse;

    @ManyToMany
    @JoinTable(
            name = "ANIMAL_VOLUNTEER", // Имя связующей таблицы
            joinColumns = @JoinColumn(name = "VOLUNTEER_ID", referencedColumnName = "ID"), // Колонка в связующей таблице для VOLUNTEER
            inverseJoinColumns = @JoinColumn(name = "ANIMAL_ID", referencedColumnName = "ID") // Колонка в связующей таблице для ANIMAL
    )
    private List<Animal> animals;

    @ManyToMany
    @JoinTable(
            name = "VOLUNTEER_ATTACHMENT", // Имя связующей таблицы
            joinColumns = @JoinColumn(name = "VOLUNTEER_ID", referencedColumnName = "ID"), // Колонка в связующей таблице для VOLUNTEER
            inverseJoinColumns = @JoinColumn(name = "ATTACHMENT_ID", referencedColumnName = "ID") // Колонка в связующей таблице для ATTACHMENT
    )
    private List<Attachment> attachments;

    @JoinColumn(name = "TELEGRAM_USER_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private TelegamUser telegramUser;

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public TelegamUser getTelegramUser() {
        return telegramUser;
    }

    public void setTelegramUser(TelegamUser telegramUser) {
        this.telegramUser = telegramUser;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
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