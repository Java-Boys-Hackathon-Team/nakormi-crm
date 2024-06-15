package ru.javaboys.nakormi.view.volunteerorder;


import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaboys.nakormi.dto.AuthUserData;
import ru.javaboys.nakormi.entity.PuckUpOrder;
import ru.javaboys.nakormi.entity.PuckupOrderStatus;
import ru.javaboys.nakormi.repository.PuckUpOrderRepository;
import ru.javaboys.nakormi.service.SecurityHelperService;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "volunteer-order-view", layout = MainView.class)
@ViewController("VolunteerOrderView")
@ViewDescriptor("volunteer-order-view.xml")
public class VolunteerOrderView extends StandardView {
    @ViewComponent private CollectionContainer<PuckUpOrder> orderDc;
    @ViewComponent private DataGrid<PuckUpOrder> orderDataGrid;
    @ViewComponent private JmixButton confirmAction;
    @ViewComponent private JmixButton rejectAction;

    @Autowired private SecurityHelperService securityHelperService;
    @Autowired private PuckUpOrderRepository puckUpOrderRepository;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) throws Exception {
        AuthUserData authUserData = securityHelperService.getAuthUserData();
        orderDc.setItems(
                puckUpOrderRepository.findByVolunteerOrderByDateDesc(authUserData.getVolunteer())
        );
    }

    @Subscribe("orderDataGrid")
    public void onClickGridItemClick(ItemClickEvent<PuckUpOrder> event) {
        updateActionButtonEnable(event.getItem().getStatus() == PuckupOrderStatus.CREATED);
    }

    @Subscribe(id = "confirmAction", subject = "clickListener")
    public void onConfirmAction(final ClickEvent<JmixButton> event) {
        PuckUpOrder order = orderDataGrid.getSingleSelectedItem();
        order.setStatus(PuckupOrderStatus.EXECUTED);
        puckUpOrderRepository.save(order);
        updateActionButtonEnable(false);
    }

    @Subscribe(id = "rejectAction", subject = "clickListener")
    public void onRejectAction(final ClickEvent<JmixButton> event) {
        PuckUpOrder order = orderDataGrid.getSingleSelectedItem();
        order.setStatus(PuckupOrderStatus.REJECTED);
        puckUpOrderRepository.save(order);
        updateActionButtonEnable(false);
    }

    private void updateActionButtonEnable(boolean enable) {
        confirmAction.setEnabled(enable);
        rejectAction.setEnabled(enable);
    }

}