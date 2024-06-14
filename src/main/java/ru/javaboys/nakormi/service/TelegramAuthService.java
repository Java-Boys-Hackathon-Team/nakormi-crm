package ru.javaboys.nakormi.service;

import io.jmix.core.Metadata;
import io.jmix.core.UnconstrainedDataManager;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import io.jmix.securityflowui.password.PasswordValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.javaboys.nakormi.entity.User;
import ru.javaboys.nakormi.security.DatabaseUserRepository;
import ru.javaboys.nakormi.security.UiMinimalRole;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TelegramAuthService {

    private final DatabaseUserRepository databaseUserRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordValidation passwordValidation;

    private final PasswordEncoder passwordEncoder;

    private final Metadata metadata;

    private final UnconstrainedDataManager unconstrainedDataManager;

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

    public void registerUser(String username,
                             String password,
//                             String firstName,
//                             String lastName,
//                             String email,
                             String telegramUserName,
                             Long telegramUserId,
                             Long telegramChatId) {

        User user = metadata.create(User.class);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
//        user.setFirstName(firstName);
//        user.setLastName(lastName);
//        user.setEmail(email);
        

        List<String> errors = passwordValidation.validate(user, password);
        if (!errors.isEmpty()) {
            throw new RuntimeException(String.join("\n", errors));
        } else {
            RoleAssignmentEntity roleAssignment = unconstrainedDataManager.create(RoleAssignmentEntity.class);
            roleAssignment.setUsername(user.getUsername());
            roleAssignment.setRoleCode(UiMinimalRole.CODE);
            roleAssignment.setRoleType(RoleAssignmentRoleType.RESOURCE);

            unconstrainedDataManager.save(user, roleAssignment);
        }

    }
}
