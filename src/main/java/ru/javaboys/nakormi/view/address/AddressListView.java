package ru.javaboys.nakormi.view.address;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import ru.javaboys.nakormi.entity.Address;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "addresses", layout = MainView.class)
@ViewController("Address.list")
@ViewDescriptor("address-list-view.xml")
@LookupComponent("addressesDataGrid")
@DialogMode(width = "64em")
public class AddressListView extends StandardListView<Address> {
}