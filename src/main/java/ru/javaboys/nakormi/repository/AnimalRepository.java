package ru.javaboys.nakormi.repository;

import io.jmix.core.repository.JmixDataRepository;
import io.jmix.core.repository.Query;
import ru.javaboys.nakormi.entity.Animal;
import ru.javaboys.nakormi.entity.Volunteer;

import java.util.List;
import java.util.UUID;

public interface AnimalRepository extends JmixDataRepository<Animal, UUID> {

    @Query("select av.animal from AnimalVolunteer av where av.volunteer = :volunteer")
    List<Animal> findOwnedByVolunteer(Volunteer volunteer);

}