package ru.javaboys.nakormi.repository;

import io.jmix.core.repository.JmixDataRepository;
import ru.javaboys.nakormi.entity.FoodTransfer;

import java.util.UUID;

public interface FoodTransferRepository extends JmixDataRepository<FoodTransfer, UUID> {
}