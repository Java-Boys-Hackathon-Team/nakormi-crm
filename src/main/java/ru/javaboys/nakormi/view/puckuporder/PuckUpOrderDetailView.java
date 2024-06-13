package ru.javaboys.nakormi.view.puckuporder;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.PuckUpOrder;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "puckUpOrders/:id", layout = MainView.class)
@ViewController("PuckUpOrder.detail")
@ViewDescriptor("puck-up-order-detail-view.xml")
@EditedEntityContainer("puckUpOrderDc")
public class PuckUpOrderDetailView extends StandardDetailView<PuckUpOrder> {
}