package ru.javaboys.nakormi.action;

import com.vaadin.flow.component.Component;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.action.ActionType;
import io.jmix.flowui.action.list.ItemTrackingAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@ActionType("uploadAction")
public class UploadAction<E> extends ItemTrackingAction<E> {

    private static final Logger log = LoggerFactory.getLogger(UploadAction.class);
    @Autowired
    private MetadataTools metadataTools;

    @Autowired
    private Notifications notifications;

    public UploadAction(String id) {
        super(id);
    }

    @Override
    public void actionPerform(Component component) {

    }
}