package ru.javaboys.nakormi.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import ru.javaboys.nakormi.entity.Food;
import ru.javaboys.nakormi.entity.Person;
import ru.javaboys.nakormi.entity.TransferTypes;
import ru.javaboys.nakormi.entity.Volunteer;
import ru.javaboys.nakormi.model.VolunteerStatData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticService {
    @PersistenceContext private EntityManager entityManager;

    public Map<TransferTypes, Long> findGoodDealCount(Volunteer volunteer) {
        List<Object[]> resultList = entityManager.createQuery("""
            select r.foodTransfer.transferType, count(distinct r.foodTransfer)
            from FoodTransferRow r
            where r.movement = 'O' and r.warehouse = :warehouse
            group by r.foodTransfer.transferType
        """, Object[].class).setParameter("warehouse", volunteer.getWarehouse()).getResultList();

        Map<TransferTypes, Long> resultMap = new HashMap<>();
        resultList.forEach(o -> {
            resultMap.put(TransferTypes.fromId((String) o[0]), (Long) o[1]);
        });
        return resultMap;
    }

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

    public Map<Food, Long> findRemaindersForVolunteer(Volunteer volunteer) {
        List<Object[]> resultList = entityManager.createQuery("""
            select
              r.food as food,
              sum(case
                when (r.movement = 'I') then r.quantity
                when (r.movement = 'O') then (r.quantity * :minus_one)
                else 0
              end) as cnt
            from FoodTransferRow r
              join Food f on f = r.food
            where r.warehouse = :warehouse
            group by r.food
            order by cnt desc
        """, Object[].class).setParameter("warehouse", volunteer.getWarehouse()).setParameter("minus_one", -1).getResultList();

        Map<Food, Long> resultMap = new HashMap<>();
        resultList.forEach(o -> {
            resultMap.put((Food) o[0], (Long) o[1]);
        });

        return resultMap;
    }

}
