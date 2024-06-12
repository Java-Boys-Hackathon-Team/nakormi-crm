package ru.javaboys.nakormi.view.district;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import ru.javaboys.nakormi.entity.District;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "districts", layout = MainView.class)
@ViewController("District.list")
@ViewDescriptor("district-list-view.xml")
@LookupComponent("districtsDataGrid")
@DialogMode(width = "64em")
public class DistrictListView extends StandardListView<District> {
}