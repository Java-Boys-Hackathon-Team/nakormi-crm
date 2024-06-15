package ru.javaboys.nakormi.service;

import io.jmix.core.security.CurrentAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.javaboys.nakormi.dto.AuthUserData;
import ru.javaboys.nakormi.entity.Person;
import ru.javaboys.nakormi.entity.PersonTypes;
import ru.javaboys.nakormi.entity.User;
import ru.javaboys.nakormi.repository.VolunteerRepository;

@Service
public class SecurityHelperService {
    @Autowired private ApplicationContext appCtx;
    @Autowired private VolunteerRepository volunteerRepository;

    public AuthUserData getAuthUserData() {
        CurrentAuthentication auth = appCtx.getBean(CurrentAuthentication.class);
        User user = (User) auth.getUser();
        Person person = user.getPerson();
        if (person != null && person.getType() == PersonTypes.VOLUNTEER) {
            return new AuthUserData(user, person, volunteerRepository.getByPerson(person));
        }

        return new AuthUserData(user, person);
    }

}
