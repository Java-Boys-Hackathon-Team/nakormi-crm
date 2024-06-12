package ru.javaboys.nakormi.view.foodtransfer;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import ru.javaboys.nakormi.entity.FoodTransfer;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "foodTransfers", layout = MainView.class)
@ViewController("FoodTransfer.list")
@ViewDescriptor("food-transfer-list-view.xml")
@LookupComponent("foodTransfersDataGrid")
@DialogMode(width = "64em")
public class FoodTransferListView extends StandardListView<FoodTransfer> {
}