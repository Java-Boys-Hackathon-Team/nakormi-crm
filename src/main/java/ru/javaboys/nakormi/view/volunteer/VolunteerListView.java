package ru.javaboys.nakormi.view.volunteer;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import ru.javaboys.nakormi.entity.Volunteer;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "volunteers", layout = MainView.class)
@ViewController("Volunteer.list")
@ViewDescriptor("volunteer-list-view.xml")
@LookupComponent("volunteersDataGrid")
@DialogMode(width = "64em")
public class VolunteerListView extends StandardListView<Volunteer> {
}