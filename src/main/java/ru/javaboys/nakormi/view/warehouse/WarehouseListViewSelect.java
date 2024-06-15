package ru.javaboys.nakormi.view.warehouse;

import com.vaadin.flow.router.Route;
import io.jmix.core.LoadContext;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.PrimaryLookupView;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.Target;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaboys.nakormi.entity.Warehouse;
import ru.javaboys.nakormi.entity.WarehouseTypes;
import ru.javaboys.nakormi.repository.WarehouseRepository;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "warehousesSelect", layout = MainView.class)
@ViewController("WarehouseSelect.list")
@ViewDescriptor("warehouse-list-view-select.xml")
@LookupComponent("warehousesDataGrid")
@DialogMode(width = "64em")
@PrimaryLookupView(Warehouse.class)
public class WarehouseListViewSelect extends StandardListView<Warehouse> {
    @Autowired WarehouseRepository warehouseRepository;

    private WarehouseTypes type;

    public void setType(WarehouseTypes type) {
        this.type = type;
    }

    @Install(to = "warehousesDl", target = Target.DATA_LOADER)
    private Iterable<Warehouse> warehouses(final LoadContext<Warehouse> loadContext) {
        if (type != null) {
            return warehouseRepository.findAllByStorageType(type.getId());
        }
        return warehouseRepository.findAll();
    }

}