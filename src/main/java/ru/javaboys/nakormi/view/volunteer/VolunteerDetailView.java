package ru.javaboys.nakormi.view.volunteer;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.core.DataManager;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.FileStorageLocator;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaboys.nakormi.entity.Attachment;
import ru.javaboys.nakormi.entity.Volunteer;
import ru.javaboys.nakormi.view.main.MainView;

import java.io.InputStream;
import java.util.Objects;

@Route(value = "volunteers/:id", layout = MainView.class)
@ViewController("Volunteer.detail")
@ViewDescriptor("volunteer-detail-view.xml")
@EditedEntityContainer("volunteerDc")
public class VolunteerDetailView extends StandardDetailView<Volunteer> {

    @ViewComponent
    private Div uploadField;

    @ViewComponent
    private Div previewWrapper;

    @Autowired
    private DataManager dataManager;

    @Autowired
    private UiComponents uiComponents;

    @Autowired
    private FileStorageLocator fileStorageLocator;

    @ViewComponent
    private DataGrid<Attachment> attachmentsDataGrid;

    @ViewComponent
    private CollectionContainer<Attachment> attachmentsDc;

    @Subscribe
    public void onInit(final InitEvent event) {
        uploadField.add(getUpload());
//        attachmentsDataGrid.setColumnPosition(getAttachmentPicture(), attachmentsDataGrid.getColumns().size() - 1);
    }

    private Grid.Column<Attachment> getAttachmentPicture() {
        return attachmentsDataGrid.addComponentColumn(attachment -> getGetImageBySource(attachment.getSource()))
                .setHeader("Attachment")
                .setFlexGrow(0);
    }

    private Component getGetImageBySource(FileRef fileRef) {
        if (Objects.isNull(fileRef) || !isImage(fileRef.getContentType())) {
            return new Span("Отсутствует превью файла.");
        }
        Image image = uiComponents.create(Image.class);
        StreamResource streamResource = new StreamResource(
                fileRef.getFileName(),
                () -> fileStorageLocator.getDefault().openStream(fileRef));
        image.setSrc(streamResource);
        return image;
    }

    private Upload getUpload() {
        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAutoUpload(true);
        upload.setDropAllowed(true);
//        upload.setMaxFileSize(11 * 1024 * 1024);
//        upload.setMaxFiles(10);
        FileStorage fileStorage = fileStorageLocator.getDefault();
        upload.addSucceededListener(e -> {
            InputStream fileStream = buffer.getInputStream(e.getFileName());
            FileRef fileRef = fileStorage.saveStream(e.getFileName(), fileStream);
            Attachment attachment = dataManager.create(Attachment.class);
            attachment.setName(fileRef.getFileName());
            attachment.setSource(fileRef);
            Attachment savedAttachment = dataManager.save(attachment);
            attachmentsDc.getMutableItems().add(savedAttachment);
        });
        return upload;
    }

    @Subscribe("attachmentsDataGrid")
    public void onAttachmentsDataGridItemClick(final ItemClickEvent<Attachment> event) {
        previewWrapper.removeAll();
        previewWrapper.add(getGetImageBySource(event.getItem().getSource()));
    }

    @Subscribe(id = "attachmentsDataGridExclude", subject = "clickListener")
    public void onAttachmentsDataGridExcludeClick(final ClickEvent<JmixButton> event) {
        previewWrapper.removeAll();
    }

    private boolean isImage(String contentType) {
        return Objects.nonNull(contentType) && contentType.startsWith("image/");
    }
}