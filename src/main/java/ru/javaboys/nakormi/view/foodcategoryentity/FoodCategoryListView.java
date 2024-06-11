package ru.javaboys.nakormi.view.foodcategoryentity;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.FoodCategory;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "foodCategories", layout = MainView.class)
@ViewController("FoodCategory.list")
@ViewDescriptor("food-category-list-view.xml")
@LookupComponent("foodCategoriesDataGrid")
@DialogMode(width = "64em")
public class FoodCategoryListView extends StandardListView<FoodCategory> {
}