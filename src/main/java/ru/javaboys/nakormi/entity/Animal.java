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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "ANIMAL", indexes = {
        @Index(name = "IDX_ANIMAL_DISTRICT", columnList = "DISTRICT_ID")
})
@Entity
public class Animal {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull(message = "Кличка животного обязательна для заполнения")
    @Column(name = "NICKNAME")
    private String nickname;

    @NotNull(message = "Тип животного обязателен для заполнения")
    @Column(name = "ANIMAL_TYPE")
    private String type;

    @JoinColumn(name = "DISTRICT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private District district;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "IS_STERILIZED", columnDefinition = "BOOLEAN default false")
    private Boolean isSterilized;

    @InstanceName
    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @ManyToMany
    @JoinTable(
            name = "ANIMAL_ATTACHMENT", // Имя связующей таблицы
            joinColumns = @JoinColumn(name = "ANIMAL_ID", referencedColumnName = "ID"), // Колонка в связующей таблице для ANIMAL
            inverseJoinColumns = @JoinColumn(name = "ATTACHMENT_ID", referencedColumnName = "ID") // Колонка в связующей таблице для ATTACHMENT
    )
    private List<Attachment> attachments;

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @ManyToMany
    @JoinTable(
            name = "ANIMAL_VOLUNTEER", // Имя связующей таблицы
            joinColumns = @JoinColumn(name = "ANIMAL_ID", referencedColumnName = "ID"), // Колонка в связующей таблице для ANIMAL
            inverseJoinColumns = @JoinColumn(name = "VOLUNTEER_ID", referencedColumnName = "ID") // Колонка в связующей таблице для VOLUNTEER
    )
    private List<Volunteer> volunteers;

    public AnimalGenders getGender() {
        return gender == null ? null : AnimalGenders.fromId(gender);
    }

    public void setGender(AnimalGenders gender) {
        this.gender = gender == null ? null : gender.getId();
    }

    public List<Volunteer> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(List<Volunteer> volunteers) {
        this.volunteers = volunteers;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsSterilized() {
        return isSterilized;
    }

    public void setIsSterilized(Boolean isSterilized) {
        this.isSterilized = isSterilized;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public AnimalTypes getType() {
        return type == null ? null : AnimalTypes.fromId(type);
    }

    public void setType(AnimalTypes animalTypes) {
        this.type = animalTypes == null ? null : animalTypes.getId();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}