package ru.javaboys.nakormi.view.food;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.Food;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "foods", layout = MainView.class)
@ViewController("Food.list")
@ViewDescriptor("food-list-view.xml")
@LookupComponent("foodsDataGrid")
@DialogMode(width = "64em")
public class FoodListView extends StandardListView<Food> {
}