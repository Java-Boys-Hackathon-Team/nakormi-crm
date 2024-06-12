package ru.javaboys.nakormi.view.foodtransfer;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.FoodTransfer;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "foodTransfers/:id", layout = MainView.class)
@ViewController("FoodTransfer.detail")
@ViewDescriptor("food-transfer-detail-view.xml")
@EditedEntityContainer("foodTransferDc")
public class FoodTransferDetailView extends StandardDetailView<FoodTransfer> {
}