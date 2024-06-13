package ru.javaboys.nakormi.view.foodtransfer;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaboys.nakormi.entity.FoodTransfer;
import ru.javaboys.nakormi.service.FoodTransferRowCSVGenerator;
import ru.javaboys.nakormi.service.VolunteerCSVGenerator;
import ru.javaboys.nakormi.service.WarehouseCSVGenerator;
import ru.javaboys.nakormi.service.YandexUploader;
import ru.javaboys.nakormi.utils.FileUtils;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "foodTransfers", layout = MainView.class)
@ViewController("FoodTransfer.list")
@ViewDescriptor("food-transfer-list-view.xml")
@LookupComponent("foodTransfersDataGrid")
@DialogMode(width = "64em")
public class FoodTransferListView extends StandardListView<FoodTransfer> {

    @Autowired
    private FoodTransferRowCSVGenerator foodTransferRowCSVGenerator;
    @Autowired
    private WarehouseCSVGenerator warehouseCSVGenerator;
    @Autowired
    VolunteerCSVGenerator volunteerCSVGenerator;
    @Autowired
    private YandexUploader yandexUploader;

    @Subscribe(id = "UploadReportBtn", subject = "clickListener")
    public void onUploadReportBtnClick(final ClickEvent<JmixButton> event) {
        FileUtils.createFolder("csv");
        foodTransferRowCSVGenerator.generateCSV();
        volunteerCSVGenerator.generateCSV();
        warehouseCSVGenerator.generateCSV();
        yandexUploader.uploadFiles();
    }
}