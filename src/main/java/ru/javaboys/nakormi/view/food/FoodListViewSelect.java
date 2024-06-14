package ru.javaboys.nakormi.view.food;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
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