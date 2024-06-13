package ru.javaboys.nakormi.view.puckuporder;

import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.apache.commons.lang3.StringUtils;
import ru.javaboys.nakormi.entity.PuckUpOrder;
import ru.javaboys.nakormi.entity.TransferTypes;
import ru.javaboys.nakormi.view.main.MainView;

import java.time.LocalDateTime;

@Route(value = "puckUpOrders/:id", layout = MainView.class)
@ViewController("PuckUpOrder.detail")
@ViewDescriptor("puck-up-order-detail-view.xml")
@EditedEntityContainer("puckUpOrderDc")
public class PuckUpOrderDetailView extends StandardDetailView<PuckUpOrder> {

    @ViewComponent private DateTimePicker dateField;
    @ViewComponent private TextField numberField;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        if (StringUtils.isBlank(numberField.getValue())) {
            numberField.setValue("Автозаполнение");
        }
        if (dateField.getValue() == null) {
            dateField.setValue(LocalDateTime.now());
        }
    }

}