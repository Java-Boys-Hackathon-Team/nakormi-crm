package ru.javaboys.nakormi.view.puckuporder;

import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.DialogWindow;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaboys.nakormi.entity.PuckUpOrder;
import ru.javaboys.nakormi.entity.Warehouse;
import ru.javaboys.nakormi.entity.WarehouseTypes;
import ru.javaboys.nakormi.view.main.MainView;
import ru.javaboys.nakormi.view.warehouse.WarehouseListViewSelect;

import java.time.LocalDateTime;

@Route(value = "puckUpOrders/:id", layout = MainView.class)
@ViewController("PuckUpOrder.detail")
@ViewDescriptor("puck-up-order-detail-view.xml")
@EditedEntityContainer("puckUpOrderDc")
public class PuckUpOrderDetailView extends StandardDetailView<PuckUpOrder> {
    @ViewComponent private EntityPicker<Warehouse> warehouseField;
    @ViewComponent private DateTimePicker dateField;
    @ViewComponent private TextField numberField;

    @Autowired private DialogWindows dialogWindows;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        if (StringUtils.isBlank(numberField.getValue())) {
            numberField.setValue("Автозаполнение");
        }
        if (dateField.getValue() == null) {
            dateField.setValue(LocalDateTime.now());
        }
    }

    @Subscribe("warehouseField.entityLookup")
    public void onWarehouseSourcePickerLookup(final ActionPerformedEvent event) {
        DialogWindow<WarehouseListViewSelect> builder = dialogWindows.lookup(warehouseField)
                .withViewClass(WarehouseListViewSelect.class)
                .build();

        builder.getView().setType(WarehouseTypes.PICKUP_POINT);
        builder.open();
    }

}