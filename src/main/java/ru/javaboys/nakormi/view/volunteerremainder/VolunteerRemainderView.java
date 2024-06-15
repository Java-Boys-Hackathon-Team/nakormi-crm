package ru.javaboys.nakormi.view.volunteerremainder;

import com.vaadin.flow.router.Route;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.flowui.model.KeyValueCollectionContainer;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaboys.nakormi.dto.AuthUserData;
import ru.javaboys.nakormi.entity.Food;
import ru.javaboys.nakormi.entity.FoodMeasureType;
import ru.javaboys.nakormi.service.SecurityHelperService;
import ru.javaboys.nakormi.service.StatisticService;
import ru.javaboys.nakormi.view.main.MainView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Route(value = "volunteer-remainder-view", layout = MainView.class)
@ViewController("VolunteerRemainderView")
@ViewDescriptor("volunteer-remainder-view.xml")
public class VolunteerRemainderView extends StandardView {
    @ViewComponent private KeyValueCollectionContainer remainderDc;

    @Autowired private StatisticService statisticService;
    @Autowired private SecurityHelperService securityHelperService;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) throws Exception {

        AuthUserData authUserData = securityHelperService.getAuthUserData();
        Map<Food, Long> remaindersData = statisticService.findRemaindersForVolunteer(authUserData.getVolunteer());

        List<KeyValueEntity> keyValueEntities = new ArrayList<>();
        remaindersData.forEach((food, cnt) -> {
            KeyValueEntity keyValueEntity = new KeyValueEntity();
            keyValueEntity.setValue("name", food.getName());
            keyValueEntity.setValue("cnt", cnt);
            keyValueEntity.setValue("measure", getMeasureStr(food));
            keyValueEntities.add(keyValueEntity);
        });

        remainderDc.setItems(keyValueEntities);
    }

    private String getMeasureStr(Food food) {
        return food.getMeasure() == FoodMeasureType.WEIGHT ? "г" : "шт";
    }

}