package ru.javaboys.nakormi.view.animalfood;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.AnimalFood;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "animalFoods/:id", layout = MainView.class)
@ViewController("AnimalFood.detail")
@ViewDescriptor("animal-food-detail-view.xml")
@EditedEntityContainer("animalFoodDc")
public class AnimalFoodDetailView extends StandardDetailView<AnimalFood> {
}