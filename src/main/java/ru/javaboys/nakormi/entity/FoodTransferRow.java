package ru.javaboys.nakormi.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "FOOD_TRANSFER_ROW", indexes = {
        @Index(name = "IDX_FOOD_TRANSFER_ROW_WAREHOUSE", columnList = "WAREHOUSE_ID"),
        @Index(name = "IDX_FOOD_TRANSFER_ROW_FOOD", columnList = "FOOD_ID"),
        @Index(name = "IDX_FOOD_TRANSFER_ROW_FOOD_TRANSFER", columnList = "FOOD_TRANSFER_ID")
})
@Entity
public class FoodTransferRow {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "FOOD_TRANSFER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FoodTransfer foodTransfer;

    @Column(name = "DATE_")
    private LocalDateTime date;

    @Column(name = "MOVEMENT")
    private String movement;

    @JoinColumn(name = "WAREHOUSE_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Warehouse warehouse;

    @JoinColumn(name = "FOOD_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Food food;

    @Column(name = "QUANTITY")
    private Integer quantity;

    public FoodTransfer getFoodTransfer() {
        return foodTransfer;
    }

    public void setFoodTransfer(FoodTransfer foodTransfer) {
        this.foodTransfer = foodTransfer;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public MovementTypes getMovement() {
        return movement == null ? null : MovementTypes.fromId(movement);
    }

    public void setMovement(MovementTypes movement) {
        this.movement = movement == null ? null : movement.getId();
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}