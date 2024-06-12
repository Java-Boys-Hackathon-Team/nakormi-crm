package ru.javaboys.nakormi.service;

import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javaboys.nakormi.entity.FoodTransferRow;
import ru.javaboys.nakormi.entity.Warehouse;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class WarehouseCSVGenerator {

    @Autowired
    DataManager dataManager;

    public void generateCSV() {
        String csvFileName = "./csv/warehouse.csv";
        List<Warehouse> rows = dataManager.load(Warehouse.class).all().list();

        try (FileWriter writer = new FileWriter(csvFileName)) {
            // Write CSV header
            writer.append("id,address,description,contacts,storageType,supervisor\n");

            // Write data for each Warehouse
            for (Warehouse warehouse : rows) {
                writer.append(warehouse.getId().toString())
                        .append(",")
                        .append(warehouse.getAddress() != null ? warehouse.getAddress().getId().toString() : "")
                        .append(",")
                        .append(warehouse.getDescription())
                        .append(",")
                        .append(warehouse.getContacts())
                        .append(",")
                        .append(warehouse.getStorageType().toString())
                        .append(",")
                        .append(warehouse.getSupervisor() != null ? warehouse.getSupervisor().getId().toString() : "")
                        .append("\n");
            }

            System.out.println("CSV file has been generated successfully at: " + csvFileName);
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }
}
