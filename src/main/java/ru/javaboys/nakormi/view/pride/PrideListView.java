package ru.javaboys.nakormi.view.pride;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.Pride;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "prides", layout = MainView.class)
@ViewController("Pride.list")
@ViewDescriptor("pride-list-view.xml")
@LookupComponent("pridesDataGrid")
@DialogMode(width = "64em")
public class PrideListView extends StandardListView<Pride> {
}