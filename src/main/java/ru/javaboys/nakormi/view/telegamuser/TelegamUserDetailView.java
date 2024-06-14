package ru.javaboys.nakormi.view.telegamuser;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.TelegamUser;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "telegamUsers/:id", layout = MainView.class)
@ViewController("TelegamUser.detail")
@ViewDescriptor("telegam-user-detail-view.xml")
@EditedEntityContainer("telegamUserDc")
public class TelegamUserDetailView extends StandardDetailView<TelegamUser> {
}