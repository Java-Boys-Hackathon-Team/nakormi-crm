package ru.javaboys.nakormi.view.warehouse;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.Warehouse;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "warehouses", layout = MainView.class)
@ViewController("Warehouse.list")
@ViewDescriptor("warehouse-list-view.xml")
@LookupComponent("warehousesDataGrid")
@DialogMode(width = "64em")
public class WarehouseListView extends StandardListView<Warehouse> {
}