package ru.javaboys.nakormi.view.warehouse;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.Warehouse;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "warehouses/:id", layout = MainView.class)
@ViewController("Warehouse.detail")
@ViewDescriptor("warehouse-detail-view.xml")
@EditedEntityContainer("warehouseDc")
public class WarehouseDetailView extends StandardDetailView<Warehouse> {
}