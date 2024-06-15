package ru.javaboys.nakormi.view.person;

import com.vaadin.flow.router.Route;
import io.jmix.core.LoadContext;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.PrimaryLookupView;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.Target;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaboys.nakormi.entity.Person;
import ru.javaboys.nakormi.entity.PersonTypes;
import ru.javaboys.nakormi.repository.PersonRepository;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "personsSelect", layout = MainView.class)
@ViewController("PersonSelect.list")
@ViewDescriptor("person-list-view-select.xml")
@LookupComponent("personsDataGrid")
@DialogMode(width = "64em")
@PrimaryLookupView(Person.class)
public class PersonListViewSelect extends StandardListView<Person> {
    @Autowired PersonRepository personRepository;

    private PersonTypes type;

    public void setType(PersonTypes type) {
        this.type = type;
    }

    @Install(to = "personsDl", target = Target.DATA_LOADER)
    private Iterable<Person> warehouses(final LoadContext<Person> loadContext) {
        if (type != null) {
            return personRepository.findAllByType(type.getId());
        }
        return personRepository.findAll();
    }


}