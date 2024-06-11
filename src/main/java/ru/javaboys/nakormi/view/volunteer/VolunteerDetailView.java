package ru.javaboys.nakormi.view.volunteer;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.Volunteer;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "volunteers/:id", layout = MainView.class)
@ViewController("Volunteer.detail")
@ViewDescriptor("volunteer-detail-view.xml")
@EditedEntityContainer("volunteerDc")
public class VolunteerDetailView extends StandardDetailView<Volunteer> {
}