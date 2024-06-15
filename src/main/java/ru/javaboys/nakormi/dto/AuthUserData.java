package ru.javaboys.nakormi.dto;

import ru.javaboys.nakormi.entity.Person;
import ru.javaboys.nakormi.entity.User;
import ru.javaboys.nakormi.entity.Volunteer;

public class AuthUserData {
    private final User user;
    private final Person person;
    private Volunteer volunteer;

    public AuthUserData(User user, Person person) {
        this.user = user;
        this.person = person;
    }

    public AuthUserData(User user, Person person, Volunteer volunteer) {
        this.user = user;
        this.person = person;
        this.volunteer = volunteer;
    }

    public User getUser() {
        return user;
    }

    public Person getPerson() {
        return person;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }
}
