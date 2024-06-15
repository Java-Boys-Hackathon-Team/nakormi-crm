package ru.javaboys.nakormi.service;

import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.core.UnconstrainedDataManager;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import io.jmix.securityflowui.password.PasswordValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.javaboys.nakormi.bot.TelegramContext;
import ru.javaboys.nakormi.entity.TelegamUser;
import ru.javaboys.nakormi.entity.User;
import ru.javaboys.nakormi.security.DatabaseUserRepository;
import ru.javaboys.nakormi.security.FullAccessRole;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TelegramService {

    private final DatabaseUserRepository databaseUserRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordValidation passwordValidation;

    private final PasswordEncoder passwordEncoder;

    private final Metadata metadata;

    private final UnconstrainedDataManager unconstrainedDataManager;

    private final DataManager dataManager;

    private final SystemAuthenticator systemAuthenticator;

    private final TelegramContext telegramContext;

    @Transactional
    public boolean authenticate(String username, String password) {
        User user = databaseUserRepository.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (authentication.isAuthenticated()) {
            return user.isAccountNonExpired() && user.isAccountNonLocked()
                    && user.isCredentialsNonExpired() && user.isEnabled();
        } else {
            return false;
        }
    }

    @Transactional
    public void registerUser(String username, String password) {

        User user = metadata.create(User.class);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setTelegramUser(telegramContext.getTelegamUser());

        List<String> errors = passwordValidation.validate(user, password);
        if (!errors.isEmpty()) {
            throw new RuntimeException(String.join("\n", errors));
        } else {
            RoleAssignmentEntity roleAssignment = unconstrainedDataManager.create(RoleAssignmentEntity.class);
            roleAssignment.setUsername(user.getUsername());
            roleAssignment.setRoleCode(FullAccessRole.CODE);
            roleAssignment.setRoleType(RoleAssignmentRoleType.RESOURCE);

            unconstrainedDataManager.save(user, roleAssignment);

            dataManager.load(User.class)
                    .query("e.id = ?1", user.getId())
                    .optional()
                    .ifPresent(u -> {
                        var telegramUser = telegramContext.getTelegamUser();
                        telegramUser.setUser(user);
                        telegramContext.setTelegamUser(dataManager.save(telegramUser));
                    });
        }

    }

    @Transactional
    public TelegamUser upsertTelegramUser(Update update) {

        String firstName = "";
        String lastName  = "";
        String userName  = "";

        Long userId = 0L;
        Long chatId = 0L;

        TelegamUser telegamUser;

        if (update.hasMessage() && update.getMessage().hasText()) {

            firstName = update.getMessage().getChat().getFirstName();
            lastName = update.getMessage().getChat().getLastName();
            userName = update.getMessage().getChat().getUserName();

            userId = update.getMessage().getFrom().getId();
            chatId = update.getMessage().getChatId();

        } else if (update.hasCallbackQuery()) {

            firstName = update.getCallbackQuery().getMessage().getChat().getFirstName();
            lastName = update.getCallbackQuery().getMessage().getChat().getLastName();
            userName = update.getCallbackQuery().getMessage().getChat().getUserName();

            userId = update.getCallbackQuery().getMessage().getChat().getId();
            chatId = update.getCallbackQuery().getMessage().getChatId();

        }

        systemAuthenticator.begin();

        var optionalTelegramUser = dataManager.load(TelegamUser.class)
                .query("e.telegramUserId = ?1", userId)
                .optional();

        TelegamUser tgUser;
        if (optionalTelegramUser.isPresent()) {
            tgUser = optionalTelegramUser.get();

            tgUser.setTelegramUserFirstName(firstName);
            tgUser.setTelegramUserLastName(lastName);
            tgUser.setTelegramUserName(userName);
            tgUser.setTelegramChatId(chatId);

        } else {

            tgUser = dataManager.create(TelegamUser.class);

            tgUser.setTelegramUserFirstName(firstName);
            tgUser.setTelegramUserLastName(lastName);
            tgUser.setTelegramUserName(userName);
            tgUser.setTelegramChatId(chatId);

            tgUser.setTelegramUserId(userId);

        }

        telegamUser = dataManager.save(tgUser);

        systemAuthenticator.end();

        telegramContext.setTelegamUser(telegamUser);

        return telegamUser;
    }
}
