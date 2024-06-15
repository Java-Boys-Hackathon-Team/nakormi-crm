package ru.javaboys.nakormi.repository;

import io.jmix.core.repository.JmixDataRepository;
import org.springframework.data.repository.query.Param;
import ru.javaboys.nakormi.entity.InvitationCode;

import java.util.List;
import java.util.UUID;

public interface InvitationCodeRepository extends JmixDataRepository<InvitationCode, UUID> {
    List<InvitationCode> findByCode(@Param("code") String code);
}
