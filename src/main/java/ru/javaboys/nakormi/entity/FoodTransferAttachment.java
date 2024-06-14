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
@Table(name = "FOOD_TRANSFER_ATTACHMENT", indexes = {
        @Index(name = "IDX_FOOD_TRANSFER_ATTACHMENT_FOOD_TRANSFER", columnList = "FOOD_TRANSFER_ID"),
        @Index(name = "IDX_FOOD_TRANSFER_ATTACHMENT_ATTACHMENT", columnList = "ATTACHMENT_ID")
})
@Entity
public class FoodTransferAttachment {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false, columnDefinition = "UUID default uuid_generate_v4()")
    @Id
    private UUID id;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "FOOD_TRANSFER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FoodTransfer foodTransfer;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "ATTACHMENT_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Attachment attachment;

    public FoodTransfer getFoodTransfer() {
        return foodTransfer;
    }

    public void setFoodTransfer(FoodTransfer foodTransfer) {
        this.foodTransfer = foodTransfer;
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