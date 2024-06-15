package ru.javaboys.nakormi.view.foodtransferrow;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.FoodTransferRow;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "foodTransferRows", layout = MainView.class)
@ViewController("FoodTransferRow.list")
@ViewDescriptor("food-transfer-row-list-view.xml")
@LookupComponent("foodTransferRowsDataGrid")
@DialogMode(width = "64em")
public class FoodTransferRowListView extends StandardListView<FoodTransferRow> {
}