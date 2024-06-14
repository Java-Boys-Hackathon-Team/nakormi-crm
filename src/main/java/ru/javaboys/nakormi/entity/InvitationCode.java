package ru.javaboys.nakormi.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@JmixEntity
@Table(name = "INVITATION_CODE", indexes = {
        @Index(name = "IDX_INVITATION_CODE_PERSON", columnList = "PERSON_ID")
})
@Entity
public class InvitationCode {

    static Random rnd = new Random(System.currentTimeMillis());

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "EXPIRATION_DATE")
    private LocalDate expirationDate;

    @InstanceName
    @Column(name = "CODE")
    private String code;

    @Column(name = "USED")
    private Boolean used;

    @JoinColumn(name = "PERSON_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Person person;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "invitationCode")
    private TelegamUser telegamUser;

    public TelegamUser getTelegamUser() {
        return telegamUser;
    }

    public void setTelegamUser(TelegamUser telegamUser) {
        this.telegamUser = telegamUser;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Boolean getUsed() {
        return used;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @PostConstruct
    public void postConstruct() {
        setExpirationDate(LocalDate.now().plusMonths(1));
        setCode(generateRandomCode(5));
    }

    private String generateRandomCode(int length) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = rnd.nextInt(alphabet.length());
            sb.append(alphabet.charAt(index));
        }
        return sb.toString();
    }
}