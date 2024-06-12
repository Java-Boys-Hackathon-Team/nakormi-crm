package ru.javaboys.nakormi.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "FOOD")
@Entity
public class Food {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinTable(name = "FOOD_FOOD_CATEGORY_LINK",
            joinColumns = @JoinColumn(name = "FOOD_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "FOOD_CATEGORY_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<FoodCategory> category;

    @NotNull(message = "Имя обязательно для заполнения")
    @NotBlank(message = "Наименование обязательно для заполнения")
    @InstanceName
    @Column(name = "NAME", columnDefinition = "VARCHAR(255)", nullable = false)
    private String name;

    @NotNull(message = "Вид фасовки обязателен для заполнения")
    @Column(name = "MEASURE", columnDefinition = "VARCHAR(255)", nullable = false)
    private String measure;

    public void setCategory(List<FoodCategory> category) {
        this.category = category;
    }

    public List<FoodCategory> getCategory() {
        return category;
    }

    public void setMeasure(FoodMeasureType measure) {
        this.measure = measure == null ? null : measure.getId();
    }

    public FoodMeasureType getMeasure() {
        return measure == null ? null : FoodMeasureType.fromId(measure);
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