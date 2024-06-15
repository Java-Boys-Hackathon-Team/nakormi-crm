package ru.javaboys.nakormi.view.invitationcode;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.model.InstanceLoader;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.Target;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.InvitationCode;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "invitationCodes", layout = MainView.class)
@ViewController("InvitationCode.list")
@ViewDescriptor("invitation-code-list-view.xml")
@LookupComponent("invitationCodesDataGrid")
@DialogMode(width = "64em")
public class InvitationCodeListView extends StandardListView<InvitationCode> {

    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<InvitationCode> invitationCodesDc;

    @ViewComponent
    private InstanceContainer<InvitationCode> invitationCodeDc;

    @ViewComponent
    private InstanceLoader<InvitationCode> invitationCodeDl;

    @ViewComponent
    private VerticalLayout listLayout;

    @ViewComponent
    private FormLayout form;

    @ViewComponent
    private HorizontalLayout detailActions;

    @Subscribe
    public void onInit(final InitEvent event) {
        updateControls(false);
    }

    @Subscribe("invitationCodesDataGrid.create")
    public void onInvitationCodesDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        InvitationCode entity = dataContext.create(InvitationCode.class);
        invitationCodeDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("invitationCodesDataGrid.edit")
    public void onInvitationCodesDataGridEdit(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveBtn")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.save();
        invitationCodesDc.replaceItem(invitationCodeDc.getItem());
        updateControls(false);
    }

    @Subscribe("cancelBtn")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        invitationCodeDl.load();
        updateControls(false);
    }

    @Subscribe(id = "invitationCodesDc", target = Target.DATA_CONTAINER)
    public void onInvitationCodesDcItemChange(final InstanceContainer.ItemChangeEvent<InvitationCode> event) {
        InvitationCode entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            invitationCodeDl.setEntityId(entity.getId());
            invitationCodeDl.load();
        } else {
            invitationCodeDl.setEntityId(null);
            invitationCodeDc.setItem(null);
        }
    }

    private void updateControls(boolean editing) {
        form.getChildren().forEach(component -> {
            if (component instanceof HasValueAndElement<?, ?> field) {
                field.setReadOnly(!editing);
            }
        });

        detailActions.setVisible(editing);
        listLayout.setEnabled(!editing);
    }
}