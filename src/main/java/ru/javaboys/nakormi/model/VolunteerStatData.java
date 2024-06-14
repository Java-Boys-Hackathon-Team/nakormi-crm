package ru.javaboys.nakormi.model;

import ru.javaboys.nakormi.entity.Person;

public class VolunteerStatData {
    private final Person person;
    private final Long cnt;

    public VolunteerStatData(Person person, Long cnt) {
        this.person = person;
        this.cnt = cnt;
    }

    public Person getPerson() {
        return person;
    }

    public Long getCnt() {
        return cnt;
    }

}
