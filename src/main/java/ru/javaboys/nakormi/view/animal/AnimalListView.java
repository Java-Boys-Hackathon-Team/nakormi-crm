package ru.javaboys.nakormi.view.animal;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.KeyValueCollectionContainer;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
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

import java.util.List;

@Route(value = "animals", layout = MainView.class)
@ViewController("Animal.list")
@ViewDescriptor("animal-list-view.xml")
@LookupComponent("animalsDataGrid")
@DialogMode(width = "64em")
public class AnimalListView extends StandardListView<Animal> {
}