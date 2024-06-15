package ru.javaboys.nakormi.view.productmovementrow;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import io.jmix.core.LoadContext;
import io.jmix.core.SaveContext;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.component.valuepicker.CustomValueSetEvent;
import io.jmix.flowui.view.*;
import org.springframework.stereotype.Component;
import ru.javaboys.nakormi.dto.ProductMovementRow;
import ru.javaboys.nakormi.entity.Food;
import ru.javaboys.nakormi.entity.FoodMeasureType;
import ru.javaboys.nakormi.view.main.MainView;

import java.util.Objects;
import java.util.Set;

@Route(value = "productMovementRows/:id", layout = MainView.class)
@ViewController("ProductMovementRow.detail")
@ViewDescriptor("product-movement-row-detail-view.xml")
@EditedEntityContainer("productMovementRowDc")
public class ProductMovementRowDetailView extends StandardDetailView<ProductMovementRow> {

    @ViewComponent
    private TextField measureTextField;

    @Install(to = "productMovementRowDl", target = Target.DATA_LOADER)
    private ProductMovementRow customerDlLoadDelegate(final LoadContext<ProductMovementRow> loadContext) {
        Object id = loadContext.getId();
        // Here you can load the entity by id from an external storage.
        // Set the loaded entity to the not-new state using EntityStates.setNew(entity, false).
        return null;
    }

    @Install(target = Target.DATA_CONTEXT)
    private Set<Object> saveDelegate(final SaveContext saveContext) {
        ProductMovementRow entity = getEditedEntity();
        // Here you can save the entity to an external storage and return the saved instance.
        // Set the returned entity to the not-new state using EntityStates.setNew(entity, false).
        // If the new entity ID is assigned by the storage, set the ID to the original instance too 
        // to let the framework match the saved instance with the original one.
        ProductMovementRow saved = entity;
        return Set.of(saved);
    }

    @Subscribe("foodField")
    public void onFoodFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityPicker<Food>, Food> event) {
        Food food = event.getValue();
        if (food == null) {
            measureTextField.setValue("");
            return;
        }

        FoodMeasureType foodMeasureType = food.getMeasure();
        if (foodMeasureType == null) {
            measureTextField.setValue("");
            return;
        }

        String measureText = "";
        String measureId = foodMeasureType.getId();
        if (measureId.equals("W")) {
            measureText = "гр.";
        } else if (measureId.equals("P")) {
            measureText = "шт.";
        }
        measureTextField.setValue(measureText);
    }
}
