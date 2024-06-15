package ru.javaboys.nakormi.repository;

import io.jmix.core.repository.JmixDataRepository;
import ru.javaboys.nakormi.entity.PuckUpOrder;
import ru.javaboys.nakormi.entity.Volunteer;

import java.util.List;
import java.util.UUID;

public interface PuckUpOrderRepository extends JmixDataRepository<PuckUpOrder, UUID> {

    List<PuckUpOrder> findByVolunteer(Volunteer volunteer);

}