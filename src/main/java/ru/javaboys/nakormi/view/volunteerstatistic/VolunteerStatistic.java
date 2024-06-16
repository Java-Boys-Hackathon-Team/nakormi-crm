package ru.javaboys.nakormi.view.volunteerstatistic;


import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "VolunteerStatistic", layout = MainView.class)
@ViewController("Volunteerstatistic")
@ViewDescriptor("VolunteerStatistic.xml")
public class VolunteerStatistic extends StandardView {
}