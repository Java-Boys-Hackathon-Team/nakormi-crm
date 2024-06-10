package ru.javaboys.nakormi.view.animalfood;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.AnimalFood;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "animalFoods", layout = MainView.class)
@ViewController("AnimalFood.list")
@ViewDescriptor("animal-food-list-view.xml")
@LookupComponent("animalFoodsDataGrid")
@DialogMode(width = "64em")
public class AnimalFoodListView extends StandardListView<AnimalFood> {
}