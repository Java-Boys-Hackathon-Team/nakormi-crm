package ru.javaboys.nakormi.view.volunteerstatistic;


import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaboys.nakormi.dto.AuthUserData;
import ru.javaboys.nakormi.entity.TransferTypes;
import ru.javaboys.nakormi.service.SecurityHelperService;
import ru.javaboys.nakormi.service.StatisticService;
import ru.javaboys.nakormi.view.main.MainView;

import java.util.Map;

@Route(value = "VolunteerStatistic", layout = MainView.class)
@ViewController("Volunteerstatistic")
@ViewDescriptor("VolunteerStatistic.xml")
public class VolunteerStatistic extends StandardView {
    @ViewComponent private H1 volunteerStatisticHeader;
    @ViewComponent private H1 goodDealCnt;
    @ViewComponent private H3 feedCnt;
    @ViewComponent private H3 benCnt;
    @ViewComponent private H3 animalCnt;

    @Autowired private StatisticService statisticService;
    @Autowired private SecurityHelperService securityHelperService;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) throws Exception {
        AuthUserData authUserData = securityHelperService.getAuthUserData();

        volunteerStatisticHeader.setText(authUserData.getPerson().getDisplayName());

        Map<TransferTypes, Long> goodDealCount = statisticService.findGoodDealCount(authUserData.getVolunteer());

        long generalCnt = goodDealCount.getOrDefault(TransferTypes.FEED, 0L) + goodDealCount.getOrDefault(TransferTypes.TRANSFER_TO_BENEFICIARY, 0L);
        goodDealCnt.setText(Long.toString(generalCnt));

        feedCnt.setText(String.format("Накормил: %s раз!", goodDealCount.getOrDefault(TransferTypes.FEED, 0L)));
        benCnt.setText(String.format("Передал благополучателям: %s раз!", goodDealCount.getOrDefault(TransferTypes.TRANSFER_TO_BENEFICIARY, 0L)));
        animalCnt.setText(String.format("У меня %s питомцев", authUserData.getVolunteer().getAnimals().size()));

    }

}