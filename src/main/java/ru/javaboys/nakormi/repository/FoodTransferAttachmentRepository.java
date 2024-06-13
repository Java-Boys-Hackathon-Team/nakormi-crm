package ru.javaboys.nakormi.repository;

import io.jmix.core.repository.JmixDataRepository;
import ru.javaboys.nakormi.entity.FoodTransferAttachment;

import java.util.UUID;

public interface FoodTransferAttachmentRepository extends JmixDataRepository<FoodTransferAttachment, UUID> {
}