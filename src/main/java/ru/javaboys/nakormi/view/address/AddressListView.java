package ru.javaboys.nakormi.view.address;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.entity.Address;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "addresses", layout = MainView.class)
@ViewController("Address.list")
@ViewDescriptor("address-list-view.xml")
@LookupComponent("addressesDataGrid")
@DialogMode(width = "64em")
public class AddressListView extends StandardListView<Address> {
}