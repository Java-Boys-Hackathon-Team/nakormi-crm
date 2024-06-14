package ru.javaboys.nakormi.service;

import io.jmix.core.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javaboys.nakormi.entity.FoodTransferRow;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class FoodTransferRowCSVGenerator {

    private static final Logger log = LoggerFactory.getLogger(FoodTransferRowCSVGenerator.class);

    @Autowired
    DataManager dataManager;

    public void generateCSV() {
        List<FoodTransferRow> rows = dataManager.load(FoodTransferRow.class).all().list();
        String csvFileName = "./csv/foodtransferrow.csv";

        try (FileWriter writer = new FileWriter(csvFileName)) {
            // Write CSV header
            writer.append("id,foodTransfer,date,movement,warehouse,food,quantity\n");

            // Write data for each FoodTransferRow
            for (FoodTransferRow row : rows) {
                writer.append(row.getId().toString())
                        .append(",")
                        .append(row.getFoodTransfer().getId().toString())
                        .append(",")
                        .append(row.getDate().toString())
                        .append(",")
                        .append(row.getMovement() != null ? row.getMovement().getId() : "")
                        .append(",")
                        .append(row.getWarehouse() != null ? row.getWarehouse().getId().toString() : "")
                        .append(",")
                        .append(row.getFood().getId().toString())
                        .append(",")
                        .append(row.getQuantity().toString())
                        .append("\n");
            }

            log.info("CSV file has been generated successfully at: " + csvFileName);
        } catch (IOException e) {
            log.error("Error writing CSV file: " + e.getMessage());
        }
    }
}
