package ru.javaboys.nakormi.view.animalfood;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.view.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaboys.nakormi.entity.AnimalFood;
import ru.javaboys.nakormi.view.main.MainView;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

@Route(value = "animalFoods", layout = MainView.class)
@ViewController("AnimalFood.list")
@ViewDescriptor("animal-food-list-view.xml")
@LookupComponent("animalFoodsDataGrid")
@DialogMode(width = "64em")
public class AnimalFoodListView extends StandardListView<AnimalFood> {

    private static final String IAM_TOKEN = "t1.9euelZqMiY_HlJWYyY2XycvNi5Gbme3rnpWai5iMmpuaz4rPj5eXz5mQjp3l8_czBF9M-e9YCyFm_N3z93MyXEz571gLIWb8zef1656Vmp3Jm4ybkJbJyMqSkcaOi8eO7_zF656Vmp3Jm4ybkJbJyMqSkcaOi8eO.99SIDXtpPFGna5GpE4xaHwBmuUePXBQHw3Ix1ZFfvpE_2f0LVolXjXiwTs6uoKgyD9glr-8qgggnynWkIQD8Bg";
    private static final String BUCKET_URL = "https://storage.yandexcloud.net/nakormi";

    private static final Logger log = LoggerFactory.getLogger(AnimalFoodListView.class);

    @Autowired
    private DataManager dataManager;

    @Subscribe("createReportBtn")
    public void getAnimalFoodsReport(ClickEvent<Button> event) throws IOException {
        createFileReport();
        uploadFileToYandexDisk();
    }

    public void createFileReport() {
        Iterable<AnimalFood> animalFoods = dataManager.load(AnimalFood.class).all().list();
        try (Workbook workbook = new SXSSFWorkbook(); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Отчет по пользователям");

            // Создаем стили для заголовков
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            // Создаем заголовки
            String[] columnNames = {"ID корма", "Название корма", "Описвние корма"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnNames.length; i++) {
                Cell cell = sheet.getRow(0).createCell(i);
                /*cell.setCellValue(columnNames[i]);
                cell.setCellStyle(headerStyle);*/
            }

            // Заполнение таблицы
            int rowNum = 1;
            for (AnimalFood animalFood : animalFoods) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(animalFood.getId() != null ? animalFood.getId().toString() : "");
                row.createCell(1).setCellValue(animalFood.getName() != null ? animalFood.getName() : "");
                row.createCell(2).setCellValue(animalFood.getDescription() != null ? animalFood.getDescription() : "");
            }

            FileOutputStream fileOut = new FileOutputStream("animal_food_report.xls");
            workbook.write(fileOut);
            fileOut.close();
        } catch (Throwable e) {
            throw new RuntimeException("Произошла ошибка во время генерации файла");
        }
    }

    public void uploadFileToYandexDisk() {
        try {
            URL url = new URL(BUCKET_URL + "/animal_food_report.xls");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", "Bearer " + IAM_TOKEN);
            connection.setDoOutput(true);


            FileInputStream fileInputStream = new FileInputStream("animal_food_report.xls");
            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer);

            connection.getOutputStream().write(buffer);

            if (connection.getResponseCode() == 201) {
                log.info("Файл успешно загружен в бакет.");
            } else {
                log.warn("Произошла ошибка при загрузке файла в бакет.");
            }

            fileInputStream.close();
            connection.disconnect();
        } catch (IOException e) {
            throw new RuntimeException("Произошла ошибка при загрузке файла в бакет");
        }
    }
}