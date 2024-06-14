package ru.javaboys.nakormi.view.productmovement;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.FileStorageLocator;
import io.jmix.core.LoadContext;
import io.jmix.core.SaveContext;
import io.jmix.flowui.action.entitypicker.EntityLookupAction;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.action.Action;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.Target;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaboys.nakormi.dto.ProductMovement;
import ru.javaboys.nakormi.entity.Attachment;
import ru.javaboys.nakormi.entity.Person;
import ru.javaboys.nakormi.entity.TransferTypes;
import ru.javaboys.nakormi.entity.Warehouse;
import ru.javaboys.nakormi.service.MovementService;
import ru.javaboys.nakormi.view.main.MainView;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;

@Route(value = "productMovements/:id", layout = MainView.class)
@ViewController("ProductMovement.detail")
@ViewDescriptor("product-movement-detail-view.xml")
@EditedEntityContainer("productMovementDc")
public class ProductMovementDetailView extends StandardDetailView<ProductMovement> {
    @ViewComponent private Div uploadField;
    @ViewComponent private Select<TransferTypes> transferTypeField;
    @ViewComponent private EntityPicker<Warehouse> warehousesSourcePicker;
    @ViewComponent private EntityPicker<Warehouse> warehousesReceiverPicker;
    @ViewComponent private EntityPicker<Person> personsPicker;
    @ViewComponent private CollectionContainer<Attachment> attachmentsDc;

    @Autowired private DataManager dataManager;
    @Autowired private FileStorageLocator fileStorageLocator;
    @Autowired private MovementService movementService;
    @ViewComponent
    private FormLayout firstScreen;
    @ViewComponent
    private FormLayout secondScreen;
    @ViewComponent
    private FormLayout thirdScreen;
    @ViewComponent
    private JmixButton prevStepSecondScreenButton;
    @ViewComponent
    private JmixButton prevStepThirdScreenButton;
    @ViewComponent
    private JmixButton nextStepFirstScreenButton;
    @ViewComponent
    private JmixButton nextStepSecondScreenButton;
    @ViewComponent
    private FormLayout variableFields;
    @ViewComponent
    private JmixButton saveAndCloseBtn;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        transferTypeField.addValueChangeListener(this::onTransferTypeFieldValueChange);
        uploadField.add(getUpload());
        firstScreenOpened();
        variableFields.setVisible(false);
    }

    @Install(to = "productMovementDl", target = Target.DATA_LOADER)
    private ProductMovement customerDlLoadDelegate(final LoadContext<ProductMovement> loadContext) {
        Object id = loadContext.getId();
        // Here you can load the entity by id from an external storage.
        // Set the loaded entity to the not-new state using EntityStates.setNew(entity, false).
        return null;
    }

    @Install(target = Target.DATA_CONTEXT)
    private Set<Object> saveDelegate(final SaveContext saveContext) {
        ProductMovement entity = getEditedEntity();
        movementService.executeMovement(entity);
        // Here you can save the entity to an external storage and return the saved instance.
        // Set the returned entity to the not-new state using EntityStates.setNew(entity, false).
        // If the new entity ID is assigned by the storage, set the ID to the original instance too
        // to let the framework match the saved instance with the original one.
        ProductMovement saved = entity;
        return Set.of(saved);
    }

    private void onTransferTypeFieldValueChange(HasValue.ValueChangeEvent<TransferTypes> event) {
        variableFields.setVisible(true);
        var visibilityInfo = visibleElementsMapInfo.getOrDefault(event.getValue(), new ElementsInfo(false, false, false));
        warehousesSourcePicker.setVisible(visibilityInfo.isWarehouseSourceEnable());
        warehousesSourcePicker.setEnabled(visibilityInfo.isWarehouseSourceEnable());
        if (!visibilityInfo.isWarehouseSourceEnable()) {
            warehousesSourcePicker.setValue(null);
        }
        warehousesReceiverPicker.setVisible(visibilityInfo.isWarehouseReceiverEnable());
        warehousesReceiverPicker.setEnabled(visibilityInfo.isWarehouseReceiverEnable());
        if (!visibilityInfo.isWarehouseReceiverEnable()) {
            warehousesReceiverPicker.setValue(null);
        }
        personsPicker.setVisible(visibilityInfo.isBeneficiaryEnable());
        personsPicker.setEnabled(visibilityInfo.isBeneficiaryEnable());
        if (!visibilityInfo.isBeneficiaryEnable()) {
            personsPicker.setValue(null);
        }
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

    private static final Map<TransferTypes, ElementsInfo> visibleElementsMapInfo = Map.of(
            TransferTypes.UNATTACHED_PICKUP,       new ElementsInfo(false, true,  false),
            TransferTypes.PICKUP_FROM_POINT,       new ElementsInfo(true,  true,  false),
            TransferTypes.TRANSFER_TO_WAREHOUSE,   new ElementsInfo(true,  true,  false),
            TransferTypes.TRANSFER_FROM_WAREHOUSE, new ElementsInfo(true,  true,  false),
            TransferTypes.TRANSFER_TO_VOLUNTEER,   new ElementsInfo(true,  true,  false),
            TransferTypes.FEED,                    new ElementsInfo(true,  false, false),
            TransferTypes.TRANSFER_TO_BENEFICIARY, new ElementsInfo(true,  false, true),
            TransferTypes.UNATTACHED_WRITEOFF,     new ElementsInfo(true,  false, false),
            TransferTypes.ACCEPTANCE_TO_WAREHOUSE, new ElementsInfo(false,  true, false),
            TransferTypes.SHIPMENT_FROM_WAREHOUSE, new ElementsInfo(true,  false, false)
    );

    private void firstScreenOpened() {
        setVisibleOnScreen(true, false, false);
        setVisibleOnNextButtons(true, false, false);
    }

    private void secondScreenOpened() {
        setVisibleOnScreen(false, true, false);
        setVisibleOnNextButtons(false, true, false);
    }

    private void thirdScreenOpened() {
        setVisibleOnScreen(false, false, true);
        setVisibleOnNextButtons(false, false, true);
    }

    private void setVisibleOnScreen(boolean firstScreenVisible,
                                  boolean secondScreenVisible,
                                  boolean thirdScreenVisible) {
        firstScreen.setVisible(firstScreenVisible);
        secondScreen.setVisible(secondScreenVisible);
        thirdScreen.setVisible(thirdScreenVisible);
    }

    private void setVisibleOnNextButtons(boolean nextStepFirstScreenButtonVisible,
                                         boolean nextStepSecondScreenButtonVisible,
                                         boolean saveAndCloseBtnVisible) {
        nextStepFirstScreenButton.setVisible(nextStepFirstScreenButtonVisible);
        nextStepSecondScreenButton.setVisible(nextStepSecondScreenButtonVisible);
        saveAndCloseBtn.setVisible(saveAndCloseBtnVisible);
    }

    @Subscribe(id = "nextStepFirstScreenButton", subject = "clickListener")
    public void onNextStepFirstScreenButtonClick(final ClickEvent<JmixButton> event) {
        secondScreenOpened();
    }

    @Subscribe(id = "nextStepSecondScreenButton", subject = "clickListener")
    public void onNextStepSecondScreenButtonClick(final ClickEvent<JmixButton> event) {
        thirdScreenOpened();
    }

    @Subscribe(id = "prevStepSecondScreenButton", subject = "clickListener")
    public void onPrevStepSecondScreenButtonClick(final ClickEvent<JmixButton> event) {
        firstScreenOpened();
    }

    @Subscribe(id = "prevStepThirdScreenButton", subject = "clickListener")
    public void onPrevStepThirdScreenButtonClick(final ClickEvent<JmixButton> event) {
        secondScreenOpened();
    }

    private static class ElementsInfo {
        private final boolean warehouseSourceEnable;
        private final boolean warehouseReceiverEnable;
        private final boolean beneficiaryEnable;

        public ElementsInfo(boolean warehouseSourceEnable, boolean warehouseReceiverEnable, boolean beneficiaryEnable) {
            this.warehouseSourceEnable = warehouseSourceEnable;
            this.warehouseReceiverEnable = warehouseReceiverEnable;
            this.beneficiaryEnable = beneficiaryEnable;
        }

        public boolean isWarehouseSourceEnable() {
            return warehouseSourceEnable;
        }

        public boolean isWarehouseReceiverEnable() {
            return warehouseReceiverEnable;
        }

        public boolean isBeneficiaryEnable() {
            return beneficiaryEnable;
        }
    }

}
