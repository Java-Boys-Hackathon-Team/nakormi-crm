package ru.javaboys.nakormi.view.food;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.PrimaryLookupView;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.Food;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "foodsSelect", layout = MainView.class)
@ViewController("FoodSelect.list")
@ViewDescriptor("food-list-view-select.xml")
@LookupComponent("foodsDataGrid")
@DialogMode(width = "64em")
@PrimaryLookupView(Food.class)
public class FoodListViewSelect extends StandardListView<Food> {
}