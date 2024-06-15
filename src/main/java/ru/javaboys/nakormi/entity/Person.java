package ru.javaboys.nakormi.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
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

import java.util.Objects;
import java.util.UUID;

@JmixEntity
@Table(name = "PERSON", indexes = {
        @Index(name = "IDX_PERSON_ADDRESS", columnList = "ADDRESS_ID")
})
@Entity
public class Person {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotNull
    @Column(name = "SURNAME", nullable = false)
    private String surname;

    @Column(name = "PHONE", length = 64)
    private String phone;

    @JoinColumn(name = "ADDRESS_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Address address;

    @Column(name = "PERSON_TYPE", nullable = false)
    private String type;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "person")
    private InvitationCode invitationCode;

    public InvitationCode getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(InvitationCode invitationCode) {
        this.invitationCode = invitationCode;
    }

    @Column(name = "GENDER", nullable = false)
    @NotNull
    private String gender;

    public PersonGenderType getGender() {
        return gender == null ? null : PersonGenderType.fromId(gender);
    }

    public void setGender(PersonGenderType gender) {
        this.gender = gender == null ? null : gender.getId();
    }

    @InstanceName
    @DependsOnProperties({"name", "surname"})
    public String getDisplayName() {
        return String.format("%s %s",
                Objects.requireNonNullElse(name, ""), Objects.requireNonNullElse(surname, ""));
    }

    public PersonTypes getType() {
        return type == null ? null : PersonTypes.fromId(type);
    }

    public void setType(PersonTypes type) {
        this.type = type == null ? null : type.getId();
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}