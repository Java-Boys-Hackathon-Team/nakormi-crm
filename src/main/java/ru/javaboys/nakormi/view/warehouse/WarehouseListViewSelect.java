package ru.javaboys.nakormi.view.warehouse;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import ru.javaboys.nakormi.entity.Warehouse;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "warehousesSelect", layout = MainView.class)
@ViewController("WarehouseSelect.list")
@ViewDescriptor("warehouse-list-view-select.xml")
@LookupComponent("warehousesDataGrid")
@DialogMode(width = "64em")
@PrimaryLookupView(Warehouse.class)
public class WarehouseListViewSelect extends StandardListView<Warehouse> {
}