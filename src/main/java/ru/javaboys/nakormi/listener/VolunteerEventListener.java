package ru.javaboys.nakormi.listener;

import io.jmix.core.DataManager;
import io.jmix.core.FileStorage;
import io.jmix.core.FileStorageLocator;
import io.jmix.core.Id;
import io.jmix.core.event.EntitySavingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.javaboys.nakormi.entity.Attachment;
import ru.javaboys.nakormi.entity.Volunteer;

import java.util.List;

@Component
public class VolunteerEventListener {

    @Autowired
    private DataManager dataManager;

    @Autowired
    private FileStorageLocator fileStorageLocator;

    @Transactional
    @EventListener
    public void onVolunteerSaving(EntitySavingEvent<Volunteer> event) {
        Volunteer volunteer = event.getEntity();
        // Загрузка текущего состояния сущности Volunteer из базы данных для сравнения
        Volunteer previousState = dataManager.load(Id.of(volunteer)).one();

        List<Attachment> previousAttachments = previousState.getAttachments();
        List<Attachment> currentAttachments = volunteer.getAttachments();

        FileStorage fileStorage = fileStorageLocator.getDefault();

        // Находим все Attachment, которые были удалены из списка
        previousAttachments.stream()
                .filter(attachment -> !currentAttachments.contains(attachment))
                .forEach(attachment -> {
                    // Удаление Attachment из базы данных
                    dataManager.remove(attachment);
                    fileStorage.removeFile(attachment.getSource());
                });
    }
}