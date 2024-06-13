package ru.javaboys.nakormi.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
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

import java.util.UUID;

@JmixEntity
@Table(name = "ANIMAL_ATTACHMENT", indexes = {
        @Index(name = "IDX_ANIMAL_ATTACHMENT_ANIMAL", columnList = "ANIMAL_ID"),
        @Index(name = "IDX_ANIMAL_ATTACHMENT_ATTACHMENT", columnList = "ATTACHMENT_ID")
})
@Entity
public class AnimalAttachment {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false, columnDefinition = "UUID default uuid_generate_v4()")
    @Id
    private UUID id;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "ANIMAL_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Animal animal;

    @JoinColumn(name = "ATTACHMENT_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Attachment attachment;

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}