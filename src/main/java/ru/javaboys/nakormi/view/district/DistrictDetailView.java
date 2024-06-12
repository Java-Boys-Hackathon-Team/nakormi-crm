package ru.javaboys.nakormi.view.district;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.District;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "districts/:id", layout = MainView.class)
@ViewController("District.detail")
@ViewDescriptor("district-detail-view.xml")
@EditedEntityContainer("districtDc")
public class DistrictDetailView extends StandardDetailView<District> {
}