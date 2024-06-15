package ru.javaboys.nakormi.view.volunteer;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import ru.javaboys.nakormi.entity.Volunteer;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "volunteersSelect", layout = MainView.class)
@ViewController("VolunteerSelect.list")
@ViewDescriptor("volunteer-list-view-select.xml")
@LookupComponent("volunteersDataGrid")
@DialogMode(width = "64em")
@PrimaryLookupView(Volunteer.class)
public class VolunteerListViewSelect extends StandardListView<Volunteer> {
}