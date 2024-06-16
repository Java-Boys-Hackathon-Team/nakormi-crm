package ru.javaboys.nakormi.view.volunteerorder;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.details.JmixDetails;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.Supply;
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

import java.time.format.DateTimeFormatter;

@Route(value = "volunteer-order-view", layout = MainView.class)
@ViewController("VolunteerOrderView")
@ViewDescriptor("volunteer-order-view.xml")
public class VolunteerOrderView extends StandardView {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    @ViewComponent private CollectionContainer<PuckUpOrder> orderDc;

    @Autowired private SecurityHelperService securityHelperService;
    @Autowired private PuckUpOrderRepository puckUpOrderRepository;
    @Autowired protected MetadataTools metadataTools;
    @Autowired protected UiComponents uiComponents;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) throws Exception {
        AuthUserData authUserData = securityHelperService.getAuthUserData();
        orderDc.setItems(
                puckUpOrderRepository.findByVolunteerOrderByDateDesc(authUserData.getVolunteer())
        );
    }

    @Supply(to = "orderList", subject = "renderer")
    protected Renderer<PuckUpOrder> virtualListRenderer() {
        return new ComponentRenderer<>(order -> {
            VerticalLayout infoLayout = createVerticalLayout();
            infoLayout.addClassNames(LumoUtility.Padding.Vertical.SMALL, LumoUtility.Gap.SMALL);

            String labelString = order.getNumberFormatted() + " от " + order.getDate().format(DATE_FORMATTER)
                    + " " + order.getWarehouse().getDescription();
            H4 orderLabel = new H4(labelString);
            Span gradeSpan = createGradeSpan(order.getStatus());
            infoLayout.add(orderLabel, gradeSpan);

            HorizontalLayout infoLine1 = createHorizontalLayout();
            infoLine1.addClassName(LumoUtility.AlignItems.CENTER);

            Span addressLabel = new Span();
            addressLabel.add(new Html("<b>Адрес:</b>"));
            Span addressDetail = new Span(order.getWarehouse().getAddress().getAddressText());

            infoLine1.add(addressLabel, addressDetail);

            HorizontalLayout infoLine2 = createHorizontalLayout();

            Span contactsLabel = new Span();
            contactsLabel.add(new Html("<b>Контакты:</b>"));
            Span contactsDetail = new Span(order.getWarehouse().getContacts());

            infoLine2.add(contactsLabel, contactsDetail);

            HorizontalLayout infoLine3 = createHorizontalLayout();

            Span supervisorLabel = new Span();
            supervisorLabel.add(new Html("<b>Ответственный:</b>"));
            Span supervisorDetail = new Span(order.getWarehouse().getSupervisor().getDisplayName());

            infoLine3.add(supervisorLabel, supervisorDetail);

            VerticalLayout additionalInfoLayout = createVerticalLayout();
            additionalInfoLayout.add(infoLine1, infoLine2, infoLine3);

            JmixDetails infoDetails = uiComponents.create(JmixDetails.class);
            infoDetails.setSummaryText("Детальная информация");
            infoDetails.setContent(additionalInfoLayout);
            infoLayout.add(infoDetails);

            if (order.getStatus() == PuckupOrderStatus.CREATED) {
                JmixDetails actionDetails = uiComponents.create(JmixDetails.class);
                actionDetails.setSummaryText("Действия");

                HorizontalLayout actionLayout = createHorizontalLayout();

                Button confirmButton = new Button();
                confirmButton.setText("Подтвердить");
                confirmButton.addClickListener(e -> {
                    order.setStatus(PuckupOrderStatus.EXECUTED);
                    puckUpOrderRepository.save(order);
                });

                Button rejectButton = new Button();
                rejectButton.setText("Отклонить");
                rejectButton.addClickListener(e -> {
                    order.setStatus(PuckupOrderStatus.REJECTED);
                    puckUpOrderRepository.save(order);
                });

                actionLayout.add(confirmButton, rejectButton);
                actionDetails.setContent(actionLayout);

                infoLayout.add(actionDetails);
            }

            infoLayout.add(new Hr());
            return infoLayout;
        });
    }

    protected VerticalLayout createVerticalLayout() {
        VerticalLayout layout = uiComponents.create(VerticalLayout.class);
        layout.setSpacing(false);
        layout.setPadding(false);
        return layout;
    }

    protected HorizontalLayout createHorizontalLayout() {
        HorizontalLayout layout = uiComponents.create(HorizontalLayout.class);
        layout.setPadding(false);
        return layout;
    }

    protected Span createGradeSpan(PuckupOrderStatus status) {
        Span gradeSpan = new Span(metadataTools.format(status));

        if (status != null) {
            ThemeList gradeThemeList = gradeSpan.getElement().getThemeList();

            switch (status) {
                case CREATED -> gradeThemeList.add("badge normal");
                case EXECUTED -> gradeThemeList.add("badge success");
                case REJECTED -> gradeThemeList.add("badge error");
                default -> gradeThemeList.add("badge");
            }
        }

        return gradeSpan;
    }

}