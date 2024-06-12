package ru.javaboys.nakormi.view.foodtransferrow;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaboys.nakormi.entity.FoodTransferRow;
import ru.javaboys.nakormi.service.FoodTransferRowCSVGenerator;
import ru.javaboys.nakormi.service.VolunteerCSVGenerator;
import ru.javaboys.nakormi.service.WarehouseCSVGenerator;
import ru.javaboys.nakormi.service.YandexUploader;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "foodTransferRows", layout = MainView.class)
@ViewController("FoodTransferRow.list")
@ViewDescriptor("food-transfer-row-list-view.xml")
@LookupComponent("foodTransferRowsDataGrid")
@DialogMode(width = "64em")
public class FoodTransferRowListView extends StandardListView<FoodTransferRow> {

    @Autowired
    private FoodTransferRowCSVGenerator csvGenerator;
    @Autowired
    private WarehouseCSVGenerator warehouseCSVGenerator;
    @Autowired
    VolunteerCSVGenerator volunteerCSVGenerator;

    @Autowired
    private YandexUploader yandexUploader;

    @Subscribe(id = "UploadReportBtn", subject = "clickListener")
    public void onUploadReportBtnClick(final ClickEvent<JmixButton> event) {
        csvGenerator.generateCSV();
        volunteerCSVGenerator.generateCSV();
        warehouseCSVGenerator.generateCSV();
        yandexUploader.uploadFiles();
    }
}