package ru.javaboys.nakormi.view.addresslookup;


import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "address-lookup-view", layout = MainView.class)
@ViewController("AddressLookupView")
@ViewDescriptor("address-lookup-view.xml")
public class AddressLookupView extends StandardView {
}