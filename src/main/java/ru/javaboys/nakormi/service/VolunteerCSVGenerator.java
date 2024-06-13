package ru.javaboys.nakormi.service;

import io.jmix.core.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javaboys.nakormi.entity.Volunteer;
import ru.javaboys.nakormi.entity.Warehouse;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class VolunteerCSVGenerator {

    private static final Logger log = LoggerFactory.getLogger(VolunteerCSVGenerator.class);

    @Autowired
    DataManager dataManager;

    public void generateCSV() {
        String csvFileName = "./csv/volunteer.csv";
        List<Volunteer> rows = dataManager.load(Volunteer.class).all().list();

        try (FileWriter writer = new FileWriter(csvFileName)) {
            // Write CSV header
            writer.append("id,person,passportNumber,telegramId,warehouse\n");

            // Write data for each Warehouse
            for (Volunteer volunteer : rows) {
                writer.append(volunteer.getId().toString())
                        .append(",")
                        .append(volunteer.getPerson() != null ? volunteer.getPerson().getId().toString() : "")
                        .append(",")
                        .append(volunteer.getPassportNumber())
                        .append(",")
                        .append(volunteer.getTelegramId())
                        .append(",")
                        .append(volunteer.getWarehouse() != null ? volunteer.getWarehouse().getId().toString() : "")
                        .append("\n");
            }

            log.info("CSV file has been generated successfully at: " + csvFileName);
        } catch (IOException e) {
            log.error("Error writing CSV file: " + e.getMessage());
        }
    }
}
