package ru.javaboys.nakormi.view.warehouse;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaboys.nakormi.entity.Warehouse;
import ru.javaboys.nakormi.view.addresslookup.AddressLookupView;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "warehouses/:id", layout = MainView.class)
@ViewController("Warehouse.detail")
@ViewDescriptor("warehouse-detail-view.xml")
@EditedEntityContainer("warehouseDc")
public class WarehouseDetailView extends StandardDetailView<Warehouse> {

    @Autowired
    private DialogWindows dialogWindows;

    @Subscribe("addressField.select")
    public void onAddressFieldSelect(final ActionPerformedEvent event) {
        dialogWindows.view(this, AddressLookupView.class).open();
    }
}