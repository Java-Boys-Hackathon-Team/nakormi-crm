package ru.javaboys.nakormi.repository;

import io.jmix.core.repository.JmixDataRepository;
import ru.javaboys.nakormi.entity.Person;

import java.util.UUID;

public interface PersonRepository extends JmixDataRepository<Person, UUID> {

    Iterable<Person> findAllByType(String type);

}