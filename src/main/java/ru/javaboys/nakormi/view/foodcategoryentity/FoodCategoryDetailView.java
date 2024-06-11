package ru.javaboys.nakormi.view.foodcategoryentity;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.FoodCategory;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "foodCategories/:id", layout = MainView.class)
@ViewController("FoodCategory.detail")
@ViewDescriptor("food-category-detail-view.xml")
@EditedEntityContainer("foodCategoryDc")
public class FoodCategoryDetailView extends StandardDetailView<FoodCategory> {
}