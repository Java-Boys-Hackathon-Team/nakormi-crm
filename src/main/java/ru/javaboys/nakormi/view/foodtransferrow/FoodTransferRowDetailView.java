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
import ru.javaboys.nakormi.utils.FileUtils;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "foodTransferRows/:id", layout = MainView.class)
@ViewController("FoodTransferRow.detail")
@ViewDescriptor("food-transfer-row-detail-view.xml")
@EditedEntityContainer("foodTransferRowDc")
public class FoodTransferRowDetailView extends StandardDetailView<FoodTransferRow> {
}