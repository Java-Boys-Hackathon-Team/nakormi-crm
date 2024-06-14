package ru.javaboys.nakormi.view.person;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import ru.javaboys.nakormi.entity.Person;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "personsSelect", layout = MainView.class)
@ViewController("PersonSelect.list")
@ViewDescriptor("person-list-view-select.xml")
@LookupComponent("personsDataGrid")
@DialogMode(width = "64em")
@PrimaryLookupView(Person.class)
public class PersonListViewSelect extends StandardListView<Person> {
}