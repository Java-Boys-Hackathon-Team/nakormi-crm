package ru.javaboys.nakormi.view.telegamuser;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.TelegamUser;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "telegamUsers", layout = MainView.class)
@ViewController("TelegamUser.list")
@ViewDescriptor("telegam-user-list-view.xml")
@LookupComponent("telegamUsersDataGrid")
@DialogMode(width = "64em")
public class TelegamUserListView extends StandardListView<TelegamUser> {
}