package ru.javaboys.nakormi.repository;

import io.jmix.core.repository.JmixDataRepository;
import ru.javaboys.nakormi.entity.Person;
import ru.javaboys.nakormi.entity.Volunteer;

import java.util.UUID;

public interface VolunteerRepository extends JmixDataRepository<Volunteer, UUID> {

    Volunteer getByPerson(Person person);

}