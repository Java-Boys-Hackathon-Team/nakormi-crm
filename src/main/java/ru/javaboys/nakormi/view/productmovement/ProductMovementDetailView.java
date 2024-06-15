package ru.javaboys.nakormi.view.productmovement;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
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
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.DialogWindow;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.StandardOutcome;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.Target;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ru.javaboys.nakormi.dto.ProductMovement;
import ru.javaboys.nakormi.dto.ProductMovementRow;
import ru.javaboys.nakormi.entity.Attachment;
import ru.javaboys.nakormi.entity.Person;
import ru.javaboys.nakormi.entity.PersonTypes;
import ru.javaboys.nakormi.entity.TransferTypes;
import ru.javaboys.nakormi.entity.User;
import ru.javaboys.nakormi.entity.Volunteer;
import ru.javaboys.nakormi.entity.Warehouse;
import ru.javaboys.nakormi.entity.WarehouseTypes;
import ru.javaboys.nakormi.repository.VolunteerRepository;
import ru.javaboys.nakormi.service.MovementService;
import ru.javaboys.nakormi.view.main.MainView;
import ru.javaboys.nakormi.view.person.PersonListViewSelect;
import ru.javaboys.nakormi.view.productmovementrow.ProductMovementRowDetailView;
import ru.javaboys.nakormi.view.warehouse.WarehouseListViewSelect;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;

@Route(value = "productMovements/:id", layout = MainView.class)
@ViewController("ProductMovement.detail")
@ViewDescriptor("product-movement-detail-view.xml")
@EditedEntityContainer("productMovementDc")
public class ProductMovementDetailView extends StandardDetailView<ProductMovement> {

    @ViewComponent private Select<TransferTypes> transferTypeField;
    @ViewComponent private EntityPicker<Volunteer> volunteerSourcePicker;
    @ViewComponent private EntityPicker<Volunteer> volunteerReceiverPicker;
    @ViewComponent private EntityPicker<Warehouse> warehouseSourcePicker;
    @ViewComponent private EntityPicker<Warehouse> warehouseReceiverPicker;
    @ViewComponent private EntityPicker<Person> beneficiaryPersonPicker;
    @ViewComponent private CollectionContainer<Attachment> attachmentsDc;
    @ViewComponent private CollectionContainer<ProductMovementRow> detailsDc;

    @ViewComponent private Component firstScreen;
    @ViewComponent private JmixButton nextStepFirstScreenButton;

    @ViewComponent private Component secondScreen;
    @ViewComponent private JmixButton prevStepSecondScreenButton;
    @ViewComponent private JmixButton nextStepSecondScreenButton;

    @ViewComponent private Component thirdScreen;
    @ViewComponent private JmixButton prevStepThirdScreenButton;

    @ViewComponent private Div uploadField;
    @ViewComponent private JmixButton saveAndCloseBtn;

    @Autowired private DataManager dataManager;
    @Autowired private FileStorageLocator fileStorageLocator;
    @Autowired private MovementService movementService;
    @Autowired private ApplicationContext appCtx;
    @Autowired private VolunteerRepository volunteerRepository;
    @Autowired private DialogWindows dialogWindows;

    // Поля заполняются при инициализации страницы
    private User currentUser;
    private Person currentPerson;
    private Volunteer currentVolunteer;
    private FirstScreenMeta firstScreenMeta = FirstScreenMeta.disabled();

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        transferTypeField.addValueChangeListener(this::onTransferTypeFieldValueChange);
        volunteerSourcePicker.addValueChangeListener(this::onSourceVolunteerChange);
        volunteerReceiverPicker.addValueChangeListener(this::onReceiverVolunteerChange);

        uploadField.add(getUpload());
        this.updateFirstScreen(FirstScreenMeta.disabled());
        this.initUserData();
        this.firstScreenOpened();
    }

    @Subscribe("warehouseSourcePicker.entityLookup")
    public void onWarehouseSourcePickerLookup(final ActionPerformedEvent event) {
        DialogWindow<WarehouseListViewSelect> builder = dialogWindows.lookup(warehouseSourcePicker)
                .withViewClass(WarehouseListViewSelect.class)
                .build();

        WarehousePickerMeta meta = firstScreenMeta.getWarehousesSourceMeta();

        builder.getView().setType(meta.getType());
        builder.open();
    }


    @Subscribe("warehouseReceiverPicker.entityLookup")
    public void onWarehouseReceiverPickerLookup(final ActionPerformedEvent event) {
        DialogWindow<WarehouseListViewSelect> builder = dialogWindows.lookup(warehouseReceiverPicker)
                .withViewClass(WarehouseListViewSelect.class)
                .build();

        WarehousePickerMeta meta = firstScreenMeta.getWarehousesReceiverMeta();

        builder.getView().setType(meta.getType());
        builder.open();
    }

    @Subscribe("beneficiaryPersonPicker.entityLookup")
    public void onBeneficiaryPersonPickerLookup(final ActionPerformedEvent event) {
        DialogWindow<PersonListViewSelect> builder = dialogWindows.lookup(beneficiaryPersonPicker)
                .withViewClass(PersonListViewSelect.class)
                .build();

        PersonPickerMeta meta = firstScreenMeta.getBeneficiaryPersonMeta();

        builder.getView().setType(meta.getType());
        builder.open();
    }

    @Subscribe(id = "nextStepFirstScreenButton", subject = "clickListener")
    public void onNextStepFirstScreenButtonClick(final ClickEvent<JmixButton> event) {
        onFirstScreenFinished();
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

    @Subscribe("productMovementRowsDataGrid.add")
    public void onProductAdd(final ActionPerformedEvent event) {
        dialogWindows.detail(this, ProductMovementRow.class)
                .withViewClass(ProductMovementRowDetailView.class)
                .newEntity()
                .withAfterCloseListener(e -> {
                    if (e.closedWith(StandardOutcome.SAVE)) {
                        ProductMovementRow entity = e.getSource().getView().getEditedEntity();
                        detailsDc.getMutableItems().add(entity);
                    }
                })
                .build()
                .open();
    }

    // Важно оставить метод, так как сейчас нет времени разбираться
    // почему иногда при выборе волонтера не подтягивается его личный склад
    private void onFirstScreenFinished() {
        if (volunteerSourcePicker.getValue() != null) {
            warehouseSourcePicker.setValue(volunteerSourcePicker.getValue().getWarehouse());
        }
        if (volunteerReceiverPicker.getValue() != null) {
            warehouseReceiverPicker.setValue(volunteerReceiverPicker.getValue().getWarehouse());
        }
    }

    private void onSourceVolunteerChange(HasValue.ValueChangeEvent<Volunteer> event) {
        if (event.getValue() == null) {
            warehouseSourcePicker.setValue(null);
        } else {
            warehouseSourcePicker.setValue(event.getValue().getWarehouse());
        }
    }

    private void onReceiverVolunteerChange(HasValue.ValueChangeEvent<Volunteer> event) {
        if (event.getValue() == null) {
            warehouseReceiverPicker.setValue(null);
        } else {
            warehouseReceiverPicker.setValue(event.getValue().getWarehouse());
        }
    }

    private void initUserData() {
        CurrentAuthentication auth = appCtx.getBean(CurrentAuthentication.class);
        User user = (User) auth.getUser();
        Person person = user.getPerson();
        if (person == null) {
            return;
        }

        this.currentUser = user;
        this.currentPerson = person;

        if (person.getType() == PersonTypes.VOLUNTEER) {
            // Волонтер
            this.currentVolunteer = volunteerRepository.getByPerson(person);

            transferTypeField.setItems(Arrays.asList(
                    TransferTypes.PICKUP_FROM_POINT,
                    TransferTypes.TRANSFER_FROM_WAREHOUSE,
                    TransferTypes.FEED,
                    TransferTypes.TRANSFER_TO_BENEFICIARY,
                    TransferTypes.TRANSFER_TO_VOLUNTEER,
                    TransferTypes.TRANSFER_TO_WAREHOUSE,
                    TransferTypes.UNATTACHED_PICKUP,
                    TransferTypes.UNATTACHED_WRITEOFF
            ));
        } else {
            // Админ
            transferTypeField.setItems(Arrays.asList(
                    TransferTypes.PICKUP_FROM_POINT,
                    TransferTypes.TRANSFER_FROM_WAREHOUSE,
                    TransferTypes.FEED,
                    TransferTypes.TRANSFER_TO_BENEFICIARY,
                    TransferTypes.TRANSFER_TO_VOLUNTEER,
                    TransferTypes.TRANSFER_TO_WAREHOUSE,
                    TransferTypes.UNATTACHED_PICKUP,
                    TransferTypes.UNATTACHED_WRITEOFF,
                    TransferTypes.ACCEPTANCE_TO_WAREHOUSE,
                    TransferTypes.SHIPMENT_FROM_WAREHOUSE
            ));
        }
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
        Volunteer volunteer = ObjectUtils.firstNonNull(volunteerSourcePicker.getValue(), volunteerReceiverPicker.getValue());
        entity.setVolunteer(volunteer);

        movementService.executeMovement(entity);
        // Here you can save the entity to an external storage and return the saved instance.
        // Set the returned entity to the not-new state using EntityStates.setNew(entity, false).
        // If the new entity ID is assigned by the storage, set the ID to the original instance too
        // to let the framework match the saved instance with the original one.
        ProductMovement saved = entity;
        return Set.of(saved);
    }

    private void onTransferTypeFieldValueChange(HasValue.ValueChangeEvent<TransferTypes> event) {
        this.firstScreenMeta = toScreenMeta(event.getValue());
        if (firstScreenMeta != null) {
            this.updateFirstScreen(firstScreenMeta);
        }
    }

    private void updateFirstScreen(FirstScreenMeta firstScreenMeta) {
        setupPicker(volunteerSourcePicker, firstScreenMeta.getVolunteerSourceMeta());
        setupPicker(volunteerReceiverPicker, firstScreenMeta.getVolunteerReceiverMeta());
        setupPicker(warehouseSourcePicker, firstScreenMeta.getWarehousesSourceMeta());
        setupPicker(warehouseReceiverPicker, firstScreenMeta.getWarehousesReceiverMeta());
        setupPicker(beneficiaryPersonPicker, firstScreenMeta.getBeneficiaryPersonMeta());
    }

    private FirstScreenMeta toScreenMeta(TransferTypes type) {
        switch (type) {
            case UNATTACHED_PICKUP -> {
                return new FirstScreenMeta(
                        FieldMeta.disabled(),
                        withVolunteerContext(
                                () -> FieldMeta.disabled(currentVolunteer),
                                () -> FieldMeta.enabled()
                        ),
                        WarehousePickerMeta.disabled(),
                        WarehousePickerMeta.disabled(),
                        PersonPickerMeta.disabled()
                );
            }
            case PICKUP_FROM_POINT -> {
                return new FirstScreenMeta(
                        FieldMeta.disabled(),
                        withVolunteerContext(
                                () -> FieldMeta.disabled(currentVolunteer),
                                () -> FieldMeta.enabled()
                        ),
                        WarehousePickerMeta.enabled(WarehouseTypes.PICKUP_POINT),
                        WarehousePickerMeta.disabled(),
                        PersonPickerMeta.disabled()
                );
            }
            case TRANSFER_TO_WAREHOUSE -> {
                return new FirstScreenMeta(
                        withVolunteerContext(
                                () -> FieldMeta.disabled(currentVolunteer),
                                () -> FieldMeta.enabled()
                        ),
                        FieldMeta.disabled(),
                        WarehousePickerMeta.disabled(),
                        WarehousePickerMeta.enabled(WarehouseTypes.STORAGE),
                        PersonPickerMeta.disabled()
                );
            }
            case TRANSFER_FROM_WAREHOUSE -> {
                return new FirstScreenMeta(
                        FieldMeta.disabled(),
                        withVolunteerContext(
                                () -> FieldMeta.disabled(currentVolunteer),
                                () -> FieldMeta.enabled()
                        ),
                        WarehousePickerMeta.enabled(WarehouseTypes.STORAGE),
                        WarehousePickerMeta.disabled(),
                        PersonPickerMeta.disabled()
                );
            }
            case TRANSFER_TO_VOLUNTEER -> {
                return new FirstScreenMeta(
                        withVolunteerContext(
                                () -> FieldMeta.disabled(currentVolunteer),
                                FieldMeta::enabled
                        ),
                        FieldMeta.enabled(),
                        WarehousePickerMeta.disabled(),
                        WarehousePickerMeta.disabled(),
                        PersonPickerMeta.disabled()
                );
            }
            case FEED -> {
                return new FirstScreenMeta(
                        withVolunteerContext(
                                () -> FieldMeta.disabled(currentVolunteer),
                                () -> FieldMeta.enabled()
                        ),
                        FieldMeta.disabled(),
                        WarehousePickerMeta.disabled(),
                        WarehousePickerMeta.disabled(),
                        PersonPickerMeta.disabled()
                );
            }
            case TRANSFER_TO_BENEFICIARY -> {
                return new FirstScreenMeta(
                        withVolunteerContext(
                                () -> FieldMeta.disabled(currentVolunteer),
                                () -> FieldMeta.enabled()
                        ),
                        FieldMeta.disabled(),
                        WarehousePickerMeta.disabled(),
                        WarehousePickerMeta.disabled(),
                        PersonPickerMeta.enabled(PersonTypes.BENEFICIARY)
                );
            }
            case UNATTACHED_WRITEOFF -> {
                return new FirstScreenMeta(
                        withVolunteerContext(
                                () -> FieldMeta.disabled(currentVolunteer),
                                () -> FieldMeta.enabled()
                        ),
                        FieldMeta.disabled(),
                        WarehousePickerMeta.disabled(),
                        WarehousePickerMeta.disabled(),
                        PersonPickerMeta.disabled()
                );
            }
            case ACCEPTANCE_TO_WAREHOUSE -> {
                return new FirstScreenMeta(
                        FieldMeta.disabled(),
                        FieldMeta.disabled(),
                        WarehousePickerMeta.disabled(),
                        withVolunteerContext(
                                () -> WarehousePickerMeta.disabled(),
                                () -> WarehousePickerMeta.enabled(WarehouseTypes.STORAGE)
                        ),
                        PersonPickerMeta.disabled()
                );
            }
            case SHIPMENT_FROM_WAREHOUSE -> {
                return new FirstScreenMeta(
                        FieldMeta.disabled(),
                        FieldMeta.disabled(),
                        withVolunteerContext(
                                () -> WarehousePickerMeta.disabled(),
                                () -> WarehousePickerMeta.enabled(WarehouseTypes.STORAGE)
                        ),
                        WarehousePickerMeta.disabled(),
                        PersonPickerMeta.disabled()
                );
            }
        }

        return null;// todo
    }

    private <V> void setupPicker(EntityPicker<V> picker, FieldMeta<V> meta) {
//        picker.setEnabled(meta.isEnable());
        picker.setVisible(meta.isEnable());
        picker.setValue(meta.getValue());
    }

    private <V, M extends FieldMeta<V>> M withVolunteerContext(Supplier<M> forVolunteer, Supplier<M> forAdmin) {
        if (currentVolunteer != null) {
            return forVolunteer.get();
        } else {
            return forAdmin.get();
        }
    }

    private Upload getUpload() {
        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAutoUpload(true);
        upload.setDropAllowed(true);
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

    private void firstScreenOpened() {
        setVisibleOnScreen(true, false, false);
        setVisibleOnNextButtons(true, false, false);
        setVisibleOnPrevButtons(false, false);
    }

    private void secondScreenOpened() {
        setVisibleOnScreen(false, true, false);
        setVisibleOnNextButtons(false, true, false);
        setVisibleOnPrevButtons(true, false);
    }

    private void thirdScreenOpened() {
        setVisibleOnScreen(false, false, true);
        setVisibleOnNextButtons(false, false, true);
        setVisibleOnPrevButtons(false, true);
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

    private void setVisibleOnPrevButtons(boolean prevStepSecondScreenButtonVisible,
                                         boolean prevStepThirdScreenButtonVisible) {
        prevStepSecondScreenButton.setVisible(prevStepSecondScreenButtonVisible);
        prevStepThirdScreenButton.setVisible(prevStepThirdScreenButtonVisible);
    }

}
