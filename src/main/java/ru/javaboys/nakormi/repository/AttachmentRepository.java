package ru.javaboys.nakormi.repository;

import io.jmix.core.repository.JmixDataRepository;
import ru.javaboys.nakormi.entity.Attachment;

import java.util.UUID;

public interface AttachmentRepository extends JmixDataRepository<Attachment, UUID> {
}