package ru.javaboys.nakormi.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@JmixEntity
@Table(name = "TELEGAM_USER", indexes = {
        @Index(name = "IDX_TELEGAM_USER_INVITATION_CODE", columnList = "INVITATION_CODE_ID")
})
@Entity
public class TelegamUser {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "TELEGRAM_USER_FIRST_NAME")
    private String telegramUserFirstName;

    @JoinColumn(name = "INVITATION_CODE_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private InvitationCode invitationCode;

    @Column(name = "TELEGRAM_USER_LAST_NAME")
    private String telegramUserLastName;

    @Column(name = "INVITATION_CODE_OK")
    private Boolean invitationCodeOk;

    @Column(name = "NAKORMI_CRM_REGISTRATION_OK")
    private Boolean nakormiCrmRegistrationOk;

    @Column(name = "NAKORMI_CRM_ACCOUNT_OK")
    private Boolean nakormiCrmAccountOk;

    @Column(name = "TELEGRAM_USER_ID")
    private Long telegramUserId;

    @Column(name = "TELEGRAM_CHAT_ID")
    private Long telegramChatId;

    @Column(name = "TELEGRAM_USER_NAME")
    private String telegramUserName;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "telegramUser")
    private User user;

    public InvitationCode getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(InvitationCode invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getTelegramUserLastName() {
        return telegramUserLastName;
    }

    public void setTelegramUserLastName(String telegramUserLastName) {
        this.telegramUserLastName = telegramUserLastName;
    }

    public String getTelegramUserFirstName() {
        return telegramUserFirstName;
    }

    public void setTelegramUserFirstName(String telegramUserFirstName) {
        this.telegramUserFirstName = telegramUserFirstName;
    }

    public Boolean getNakormiCrmRegistrationOk() {
        return nakormiCrmRegistrationOk;
    }

    public void setNakormiCrmRegistrationOk(Boolean nakormiCrmRegistrationOk) {
        this.nakormiCrmRegistrationOk = nakormiCrmRegistrationOk;
    }

    public Boolean getNakormiCrmAccountOk() {
        return nakormiCrmAccountOk;
    }

    public void setNakormiCrmAccountOk(Boolean nakormiCrmAccountOk) {
        this.nakormiCrmAccountOk = nakormiCrmAccountOk;
    }

    public Boolean getInvitationCodeOk() {
        return invitationCodeOk;
    }

    public void setInvitationCodeOk(Boolean invitationCodeOk) {
        this.invitationCodeOk = invitationCodeOk;
    }

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