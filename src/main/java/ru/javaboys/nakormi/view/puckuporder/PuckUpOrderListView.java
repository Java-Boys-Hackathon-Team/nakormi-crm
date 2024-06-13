package ru.javaboys.nakormi.view.puckuporder;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import ru.javaboys.nakormi.entity.PuckUpOrder;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "puckUpOrders", layout = MainView.class)
@ViewController("PuckUpOrder.list")
@ViewDescriptor("puck-up-order-list-view.xml")
@LookupComponent("puckUpOrdersDataGrid")
@DialogMode(width = "64em")
public class PuckUpOrderListView extends StandardListView<PuckUpOrder> {
}