package ru.javaboys.nakormi.view.volunteer;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.PrimaryLookupView;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
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