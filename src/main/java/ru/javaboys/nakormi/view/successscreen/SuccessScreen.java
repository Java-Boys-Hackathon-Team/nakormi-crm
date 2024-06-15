package ru.javaboys.nakormi.view.successscreen;


import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "success")
@ViewController("SuccessScreen")
@ViewDescriptor("success-screen.xml")
public class SuccessScreen extends StandardView {

    @Autowired
    private ViewNavigators viewNavigators;

    @Subscribe(id = "button", subject = "clickListener")
    public void onButtonClick(final ClickEvent<JmixButton> event) {
        viewNavigators.view(MainView.class).navigate();
    }
}