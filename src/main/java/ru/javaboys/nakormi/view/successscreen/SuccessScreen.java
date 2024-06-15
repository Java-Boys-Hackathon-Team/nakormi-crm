package ru.javaboys.nakormi.view.successscreen;


import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaboys.nakormi.utils.InMemoryImageSource;
import ru.javaboys.nakormi.view.main.MainView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

@Route(value = "success")
@ViewController("SuccessScreen")
@ViewDescriptor("success-screen.xml")
public class SuccessScreen extends StandardView {

    @ViewComponent
    private JmixImage<byte[]> succesImage;

    @Autowired
    private ViewNavigators viewNavigators;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) throws IOException {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        BufferedImage baseImg = ImageIO.read(
                Objects.requireNonNull(contextClassLoader.getResource("META-INF/resources/icons/green_tick.jpg")));
//        StreamResource resource = new StreamResource("green_tick.jpg",
//                () -> getClass().getResourceAsStream("META-INF/resources/icons/green_tick.jpg"));
        succesImage.setValueSource(new InMemoryImageSource(baseImg));
    }

    @Subscribe(id = "button", subject = "clickListener")
    public void onButtonClick(final ClickEvent<JmixButton> event) throws Exception {
        viewNavigators.view(MainView.class).navigate();
    }
}