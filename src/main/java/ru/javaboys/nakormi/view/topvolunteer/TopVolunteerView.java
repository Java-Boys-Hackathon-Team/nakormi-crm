package ru.javaboys.nakormi.view.topvolunteer;

import com.vaadin.flow.router.Route;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.model.KeyValueCollectionContainer;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaboys.nakormi.model.VolunteerStatData;
import ru.javaboys.nakormi.service.StatisticService;
import ru.javaboys.nakormi.service.VolunteerTopByFoodImgService;
import ru.javaboys.nakormi.utils.InMemoryImageSource;
import ru.javaboys.nakormi.view.main.MainView;

import java.awt.image.BufferedImage;
import java.util.List;

@Route(value = "top-volunteer-view", layout = MainView.class)
@ViewController("TopVolunteerView")
@ViewDescriptor("top-volunteer-view.xml")
public class TopVolunteerView extends StandardView {
    @ViewComponent private JmixImage<byte[]> volunteerTopByFoodImg;
    @ViewComponent private KeyValueCollectionContainer volunteerTopByFoodDc;

    @Autowired private VolunteerTopByFoodImgService volunteerTopByFoodImgService;
    @Autowired private StatisticService statisticService;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) throws Exception {

        List<VolunteerStatData> volunteerStatDataList = statisticService.findVolunteerStatisticByFood();

        List<KeyValueEntity> keyValueEntities = volunteerStatDataList.stream()
                .map(sd -> {
                    KeyValueEntity keyValueEntity = new KeyValueEntity();
                    keyValueEntity.setValue("fio", sd.getPerson().getDisplayName());
                    keyValueEntity.setValue("cnt", sd.getCnt());
                    return keyValueEntity;
                }).toList();
        volunteerTopByFoodDc.setItems(keyValueEntities);

        BufferedImage statImage = volunteerTopByFoodImgService.buildImage(volunteerStatDataList);
        volunteerTopByFoodImg.setValueSource(new InMemoryImageSource(statImage));
    }

}