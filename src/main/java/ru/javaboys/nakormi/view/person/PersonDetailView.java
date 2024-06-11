package ru.javaboys.nakormi.view.person;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.Person;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "persons/:id", layout = MainView.class)
@ViewController("Person.detail")
@ViewDescriptor("person-detail-view.xml")
@EditedEntityContainer("personDc")
public class PersonDetailView extends StandardDetailView<Person> {
}