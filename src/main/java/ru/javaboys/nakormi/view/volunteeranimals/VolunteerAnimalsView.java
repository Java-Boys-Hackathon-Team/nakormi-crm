package ru.javaboys.nakormi.view.volunteeranimals;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaboys.nakormi.dto.AuthUserData;
import ru.javaboys.nakormi.entity.Animal;
import ru.javaboys.nakormi.repository.AnimalRepository;
import ru.javaboys.nakormi.service.SecurityHelperService;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "volunteer-animals-view", layout = MainView.class)
@ViewController("VolunteerAnimalsView")
@ViewDescriptor("volunteer-animals-view.xml")
public class VolunteerAnimalsView extends StandardView {
    @ViewComponent private CollectionContainer<Animal> animalDc;

    @Autowired private SecurityHelperService securityHelperService;
    @Autowired private AnimalRepository animalRepository;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) throws Exception {
        AuthUserData authUserData = securityHelperService.getAuthUserData();
        animalDc.setItems(
                animalRepository.findOwnedByVolunteer(authUserData.getVolunteer())
        );
    }

}