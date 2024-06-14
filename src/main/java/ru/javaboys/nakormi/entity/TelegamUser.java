package ru.javaboys.nakormi.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@JmixEntity
@Table(name = "TELEGAM_USER")
@Entity
public class TelegamUser {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "TELEGRAM_USER_ID")
    private Long telegramUserId;

    @Column(name = "TELEGRAM_CHAT_ID")
    private Long telegramChatId;

    @Column(name = "TELEGRAM_USER_NAME")
    private String telegramUserName;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "telegramUser")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTelegramUserName() {
        return telegramUserName;
    }

    public void setTelegramUserName(String telegramUserName) {
        this.telegramUserName = telegramUserName;
    }

    public Long getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(Long telegramChatId) {
        this.telegramChatId = telegramChatId;
    }

    public Long getTelegramUserId() {
        return telegramUserId;
    }

    public void setTelegramUserId(Long telegramUserId) {
        this.telegramUserId = telegramUserId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}