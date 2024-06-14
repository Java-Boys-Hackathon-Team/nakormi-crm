package ru.javaboys.nakormi.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import ru.javaboys.nakormi.entity.Person;
import ru.javaboys.nakormi.model.VolunteerStatData;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticService {
    @PersistenceContext private EntityManager entityManager;

    public List<VolunteerStatData> findVolunteerStatisticByFood() {
        List<Object[]> resultList = entityManager.createQuery("""
            select
              v.person as person,
              sum(case
                when (r.food.measure = 'P') then (r.quantity * r.food.weight)
                when (r.food.measure = 'W') then r.quantity
                else 0
              end) as cnt
            from Volunteer v
              join FoodTransferRow r on r.warehouse = v.warehouse
            where r.foodTransfer.transferType in ('FED', 'TTB')
            group by v.person
            order by cnt desc
        """, Object[].class).getResultList();

        return resultList.stream()
                .map(o -> new VolunteerStatData((Person) o[0], (Long) o[1]))
                .collect(Collectors.toList());
    }

}
