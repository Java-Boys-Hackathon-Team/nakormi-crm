package ru.javaboys.nakormi.service;

import io.jmix.core.DataManager;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.FileStorageLocator;
import io.jmix.core.security.SystemAuthenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.javaboys.nakormi.entity.Attachment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Component
@RequiredArgsConstructor
public class AttachmentService {

    private final FileStorageLocator fileStorageLocator;
    private final SystemAuthenticator systemAuthenticator;
    private final DataManager dataManager;

    public Attachment save(File file, String fileId, String fileName) throws FileNotFoundException {
        FileStorage fileStorage = fileStorageLocator.getDefault();

        FileRef fileRef = fileStorage.saveStream(fileName, new FileInputStream(file));

        systemAuthenticator.begin();

        Attachment attachment = dataManager.create(Attachment.class);
        attachment.setName(fileRef.getFileName());
        attachment.setSource(fileRef);
        attachment.setTelegramFileId(fileId);
        Attachment savedAttachment = dataManager.save(attachment);

        systemAuthenticator.end();

        return savedAttachment;
    }
}
