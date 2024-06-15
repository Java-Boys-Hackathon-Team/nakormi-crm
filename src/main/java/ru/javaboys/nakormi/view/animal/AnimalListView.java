package ru.javaboys.nakormi.view.animal;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.Animal;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "animals", layout = MainView.class)
@ViewController("Animal.list")
@ViewDescriptor("animal-list-view.xml")
@LookupComponent("animalsDataGrid")
@DialogMode(width = "64em")
public class AnimalListView extends StandardListView<Animal> {
}