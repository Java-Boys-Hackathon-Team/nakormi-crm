package ru.javaboys.nakormi.repository;

import io.jmix.core.repository.JmixDataRepository;
import ru.javaboys.nakormi.entity.AnimalFood;

import java.util.UUID;

public interface AnimalFoodRepository extends JmixDataRepository<AnimalFood, UUID> {
}
