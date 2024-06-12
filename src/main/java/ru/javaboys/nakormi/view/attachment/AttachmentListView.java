package ru.javaboys.nakormi.view.attachment;

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
import ru.javaboys.nakormi.entity.Attachment;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "attachments", layout = MainView.class)
@ViewController("Attachment.list")
@ViewDescriptor("attachment-list-view.xml")
@LookupComponent("attachmentsDataGrid")
@DialogMode(width = "64em")
public class AttachmentListView extends StandardListView<Attachment> {

    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<Attachment> attachmentsDc;

    @ViewComponent
    private InstanceContainer<Attachment> attachmentDc;

    @ViewComponent
    private InstanceLoader<Attachment> attachmentDl;

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

    @Subscribe("attachmentsDataGrid.create")
    public void onAttachmentsDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        Attachment entity = dataContext.create(Attachment.class);
        attachmentDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("attachmentsDataGrid.edit")
    public void onAttachmentsDataGridEdit(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveBtn")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.save();
        attachmentsDc.replaceItem(attachmentDc.getItem());
        updateControls(false);
    }

    @Subscribe("cancelBtn")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        attachmentDl.load();
        updateControls(false);
    }

    @Subscribe(id = "attachmentsDc", target = Target.DATA_CONTAINER)
    public void onAttachmentsDcItemChange(final InstanceContainer.ItemChangeEvent<Attachment> event) {
        Attachment entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            attachmentDl.setEntityId(entity.getId());
            attachmentDl.load();
        } else {
            attachmentDl.setEntityId(null);
            attachmentDc.setItem(null);
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