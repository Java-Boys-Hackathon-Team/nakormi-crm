package ru.javaboys.nakormi.view.food;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.Food;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "foods/:id", layout = MainView.class)
@ViewController("Food.detail")
@ViewDescriptor("food-detail-view.xml")
@EditedEntityContainer("foodDc")
public class FoodDetailView extends StandardDetailView<Food> {
}