package ru.javaboys.nakormi.view.addresslookup;


import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.mapsflowui.component.model.source.DataVectorSource;
import io.jmix.mapsflowui.kit.component.model.Padding;
import io.jmix.mapsflowui.kit.component.model.style.Fill;
import io.jmix.mapsflowui.kit.component.model.style.Style;
import io.jmix.mapsflowui.kit.component.model.style.image.Anchor;
import io.jmix.mapsflowui.kit.component.model.style.image.IconOrigin;
import io.jmix.mapsflowui.kit.component.model.style.image.IconStyle;
import io.jmix.mapsflowui.kit.component.model.style.text.TextStyle;
import ru.javaboys.nakormi.entity.Address;
import ru.javaboys.nakormi.view.main.MainView;

import java.beans.PropertyChangeEvent;

@Route(value = "address-lookup-view", layout = MainView.class)
@ViewController("AddressLookupView")
@ViewDescriptor("address-lookup-view.xml")
@DialogMode(width = "60em", height = "45em")
public class AddressLookupView extends StandardView {

    @ViewComponent("map.dataVectorLayer.buildingSource")
    private DataVectorSource<Address> buildingSource;

    @Subscribe("select")
    public void onSelectPropertyChange(final PropertyChangeEvent event) {
        close(StandardOutcome.SELECT);
    }

    private void initBuildingSource(){
        buildingSource.setStyleProvider(address -> new Style()
                .withImage(new IconStyle()
                        .withScale(0.5)
                        .withAnchorOrigin(IconOrigin.BOTTOM_LEFT)
                        .withAnchor(new Anchor(0.49, 0.12))
                        .withSrc("icons/address-marker.png"))
                .withText(new TextStyle()
                        .withBackgroundFill(new Fill("rgba(255, 255, 255, 0.6)"))
                        .withPadding(new Padding(5, 5, 5, 5))
                        .withOffsetY(15)
                        .withFont("bold 15px sans-serif")
                        .withText(address.getAddressText())));
    }

    @Subscribe
    public void onInit(final InitEvent event) {
        initBuildingSource();
    }
}