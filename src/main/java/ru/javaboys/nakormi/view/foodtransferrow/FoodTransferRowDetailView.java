package ru.javaboys.nakormi.view.foodtransferrow;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.FoodTransferRow;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "foodTransferRows/:id", layout = MainView.class)
@ViewController("FoodTransferRow.detail")
@ViewDescriptor("food-transfer-row-detail-view.xml")
@EditedEntityContainer("foodTransferRowDc")
public class FoodTransferRowDetailView extends StandardDetailView<FoodTransferRow> {
}