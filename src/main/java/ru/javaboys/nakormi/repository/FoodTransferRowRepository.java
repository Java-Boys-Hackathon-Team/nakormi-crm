package ru.javaboys.nakormi.repository;

import io.jmix.core.repository.JmixDataRepository;
import ru.javaboys.nakormi.entity.FoodTransferRow;

import java.util.UUID;

public interface FoodTransferRowRepository extends JmixDataRepository<FoodTransferRow, UUID> {
}