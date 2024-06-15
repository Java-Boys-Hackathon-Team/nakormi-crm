package ru.javaboys.nakormi.view.food;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.textfield.JmixIntegerField;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.apache.poi.util.IntegerField;
import ru.javaboys.nakormi.entity.Food;
import ru.javaboys.nakormi.entity.FoodMeasureType;
import ru.javaboys.nakormi.entity.TransferTypes;
import ru.javaboys.nakormi.view.main.MainView;
import ru.javaboys.nakormi.view.productmovement.ProductMovementDetailView;

@Route(value = "foods/:id", layout = MainView.class)
@ViewController("Food.detail")
@ViewDescriptor("food-detail-view.xml")
@EditedEntityContainer("foodDc")
public class FoodDetailView extends StandardDetailView<Food> {
    @ViewComponent private Select<FoodMeasureType> measureField;
    @ViewComponent private JmixIntegerField weightField;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        measureField.addValueChangeListener(this::onMeasureFieldFieldValueChange);

    }

    private void onMeasureFieldFieldValueChange(HasValue.ValueChangeEvent<FoodMeasureType> event) {
        if (event.getValue() == FoodMeasureType.PIECE) {
            weightField.setEnabled(true);
        }
        if (event.getValue() == FoodMeasureType.WEIGHT) {
            weightField.setEnabled(false);
            weightField.setValue(null);
        }
    }

}