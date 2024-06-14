package ru.javaboys.nakormi.view.pride;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.Pride;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "prides/:id", layout = MainView.class)
@ViewController("Pride.detail")
@ViewDescriptor("pride-detail-view.xml")
@EditedEntityContainer("prideDc")
public class PrideDetailView extends StandardDetailView<Pride> {
}