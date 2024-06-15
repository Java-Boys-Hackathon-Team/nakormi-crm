package ru.javaboys.nakormi.view.volunteeranimals;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.core.FileStorage;
import io.jmix.core.FileStorageLocator;
import io.jmix.core.Messages;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.Supply;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaboys.nakormi.dto.AuthUserData;
import ru.javaboys.nakormi.entity.Animal;
import ru.javaboys.nakormi.entity.Attachment;
import ru.javaboys.nakormi.repository.AnimalRepository;
import ru.javaboys.nakormi.service.SecurityHelperService;
import ru.javaboys.nakormi.view.main.MainView;

import java.io.InputStream;
import java.util.List;

@Route(value = "volunteer-animals-view", layout = MainView.class)
@ViewController("VolunteerAnimalsView")
@ViewDescriptor("volunteer-animals-view.xml")
public class VolunteerAnimalsView extends StandardView {
    @ViewComponent private CollectionContainer<Animal> animalDc;
    @ViewComponent private H5 topTitle;

    @Autowired private SecurityHelperService securityHelperService;
    @Autowired private FileStorageLocator fileStorageLocator;
    @Autowired private AnimalRepository animalRepository;
    @Autowired protected MetadataTools metadataTools;
    @Autowired protected UiComponents uiComponents;
    @Autowired private Messages messages;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) throws Exception {
        AuthUserData authUserData = securityHelperService.getAuthUserData();
        List<Animal> ownedByVolunteer = animalRepository.findOwnedByVolunteer(authUserData.getVolunteer());

        if (!ownedByVolunteer.isEmpty()) {
            topTitle.setText(String.format("В мире стало на %s счастливых животных больше", ownedByVolunteer.size()));
        }
        animalDc.setItems(ownedByVolunteer);
    }

    @Supply(to = "animalList", subject = "renderer")
    private Renderer<Animal> animalListRenderer() {
        return new ComponentRenderer<>(animal -> {
            HorizontalLayout rootCardLayout = new HorizontalLayout();
            rootCardLayout.setMargin(true);

            Avatar avatar = new Avatar();
            avatar.getStyle().set("--vaadin-avatar-size", "5rem");

            FileStorage fileStorage = fileStorageLocator.getDefault();
            Attachment logoAttachment = animal.getAttachments().getFirst();
            InputStream is = fileStorage.openStream(logoAttachment.getSource());
            avatar.setImageResource(new StreamResource(logoAttachment.getName(), () -> is));

            VerticalLayout infoLayout = new VerticalLayout();
            infoLayout.setSpacing(false);
            infoLayout.setPadding(false);
            infoLayout.getStyle().set("font-size", "0.75rem");

            String animalType = messages.getMessage(animal.getType().getClass().getPackageName() + "/AnimalTypes." + animal.getType().name());
            String animalName = animal.getNickname();

            infoLayout.add(new H4("[" + animalType + "] " + animalName));

            HorizontalLayout prideLine = createHorizontalLayout();
            prideLine.add(new Html("<b>Прайд:</b>"));

            if (animal.getPride() != null) {
                prideLine.add(new Span(animal.getPride().getName()));
            } else {
                prideLine.add(new Span("Не состоит"));
            }
            infoLayout.add(prideLine);

            HorizontalLayout genderLine = createHorizontalLayout();
            {
                Span contactsLabel = new Span();
                contactsLabel.add(new Html("<b>Пол:</b>"));

                Span genderDetail = null;
                if (animal.getGender() != null) {
                    genderDetail = new Span(messages.getMessage(animal.getGender().getClass().getPackageName() + "/AnimalGenders." + animal.getGender().name()));
                } else {
                    genderDetail = new Span("Неизвестен");
                }

                genderLine.add(contactsLabel, genderDetail);
            }
            infoLayout.add(genderLine);

            HorizontalLayout sterilizedLine = createHorizontalLayout();
            {
                Span sterilizedLabel = new Span();
                sterilizedLabel.add(new Html("<b>Стерилизован:</b>"));

                Span sterilizedDetail = null;
                if (animal.getIsSterilized() != null) {
                    sterilizedDetail = new Span(animal.getIsSterilized() ? "Да" : "Нет");
                } else {
                    sterilizedDetail = new Span("Неизвестно");
                }

                sterilizedLine.add(sterilizedLabel, sterilizedDetail);
            }
            infoLayout.add(sterilizedLine);

            rootCardLayout.add(avatar, infoLayout, new Hr());
            return rootCardLayout;
        });
    }

    protected VerticalLayout createVerticalLayout() {
        VerticalLayout layout = uiComponents.create(VerticalLayout.class);
        layout.setSpacing(false);
        layout.setPadding(false);
        return layout;
    }

    protected HorizontalLayout createHorizontalLayout() {
        HorizontalLayout layout = uiComponents.create(HorizontalLayout.class);
        layout.setPadding(false);
        return layout;
    }

}