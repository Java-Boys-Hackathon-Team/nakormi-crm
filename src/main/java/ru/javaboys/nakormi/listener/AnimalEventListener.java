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
import ru.javaboys.nakormi.entity.Animal;
import ru.javaboys.nakormi.entity.Attachment;

import java.util.List;
import java.util.Objects;

@Component
public class AnimalEventListener {

    @Autowired
    private DataManager dataManager;

    @Autowired
    private FileStorageLocator fileStorageLocator;

    @Transactional
    @EventListener
    public void onAnimalSaving(EntitySavingEvent<Animal> event) {
        Animal animal = event.getEntity();
        // Загрузка текущего состояния сущности Animal из базы данных для сравнения
        Animal previousState = dataManager.load(Id.of(animal))
                .optional()
                .orElse(null);
        if (Objects.isNull(previousState)) {
            return;
        }

        List<Attachment> previousAttachments = previousState.getAttachments();
        List<Attachment> currentAttachments = animal.getAttachments();

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