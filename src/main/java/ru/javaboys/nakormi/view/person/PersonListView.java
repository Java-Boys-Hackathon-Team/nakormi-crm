package ru.javaboys.nakormi.view.person;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import ru.javaboys.nakormi.entity.Person;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "persons", layout = MainView.class)
@ViewController("Person.list")
@ViewDescriptor("person-list-view.xml")
@LookupComponent("personsDataGrid")
@DialogMode(width = "64em")
public class PersonListView extends StandardListView<Person> {
}