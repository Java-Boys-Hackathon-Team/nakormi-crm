package ru.javaboys.nakormi.entity;

import io.jmix.core.FileRef;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import java.util.UUID;

@JmixEntity
@Table(name = "ATTACHMENT")
@Entity
public class Attachment {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "TELEGRAM_FILE_ID")
    private String telegramFileId;

    @InstanceName
    @Column(name = "NAME")
    private String name;

    @Lob
    @Column(name = "SOURCE")
    private FileRef source;

    public String getTelegramFileId() {
        return telegramFileId;
    }

    public void setTelegramFileId(String telegramFileId) {
        this.telegramFileId = telegramFileId;
    }

    public FileRef getSource() {
        return source;
    }

    public void setSource(FileRef source) {
        this.source = source;
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