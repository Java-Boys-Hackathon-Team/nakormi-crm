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
@Table(name = "VOLUNTEER_ATTACHMENT", indexes = {
        @Index(name = "IDX_VOLUNTEER_ATTACHMENT_VOLUNTEER", columnList = "VOLUNTEER_ID"),
        @Index(name = "IDX_VOLUNTEER_ATTACHMENT_ATTACHMENT", columnList = "ATTACHMENT_ID")
})
@Entity
public class VolunteerAttachment {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false, columnDefinition = "UUID default uuid_generate_v4()")
    @Id
    private UUID id;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "VOLUNTEER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Volunteer volunteer;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "ATTACHMENT_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Attachment attachment;

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
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