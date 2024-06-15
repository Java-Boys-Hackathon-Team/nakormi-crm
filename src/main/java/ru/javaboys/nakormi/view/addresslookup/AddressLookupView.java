package ru.javaboys.nakormi.view.addresslookup;


import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.action.BaseAction;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.StandardOutcome;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.mapsflowui.component.GeoMap;
import io.jmix.mapsflowui.component.model.FitOptions;
import io.jmix.mapsflowui.component.model.source.DataVectorSource;
import io.jmix.mapsflowui.kit.component.model.Easing;
import io.jmix.mapsflowui.kit.component.model.Padding;
import io.jmix.mapsflowui.kit.component.model.style.Fill;
import io.jmix.mapsflowui.kit.component.model.style.Style;
import io.jmix.mapsflowui.kit.component.model.style.image.Anchor;
import io.jmix.mapsflowui.kit.component.model.style.image.IconOrigin;
import io.jmix.mapsflowui.kit.component.model.style.image.IconStyle;
import io.jmix.mapsflowui.kit.component.model.style.text.TextStyle;
import org.locationtech.jts.geom.Geometry;
import ru.javaboys.nakormi.entity.Address;
import ru.javaboys.nakormi.view.main.MainView;

import java.beans.PropertyChangeEvent;
import java.util.Objects;

@Route(value = "address-lookup-view", layout = MainView.class)
@ViewController("AddressLookupView")
@ViewDescriptor("address-lookup-view.xml")
@DialogMode(width = "60em", height = "45em")
public class AddressLookupView extends StandardView {

    @ViewComponent("map.dataVectorLayer.buildingSource")
    private DataVectorSource<Address> buildingSource;
    @ViewComponent
    private GeoMap map;

    @ViewComponent
    private EntityPicker<Address> currentAddressField;

    @ViewComponent
    private BaseAction select;

    private Address selected;

    @Subscribe("select")
    public void onSelectPropertyChange(final PropertyChangeEvent event) {
        close(StandardOutcome.SELECT);
    }

    private void initBuildingSource() {
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

        buildingSource.addGeoObjectClickListener(clickEvent -> {
            Address address = clickEvent.getItem();

            setMapCenter(address.getCoordinate());

            onAddressChange(address);
        });
    }

    @Subscribe
    public void onInit(final InitEvent event) {
        initBuildingSource();
    }

    private void setMapCenter(Geometry center) {
        map.fit(new FitOptions(center)
                .withDuration(2000)
                .withEasing(Easing.LINEAR)
                .withMaxZoom(20d));
    }

    private void onAddressChange(Address newAddress) {
        if (newAddress != null)
            if (!Objects.equals(newAddress, selected)) {
                selected = newAddress;
                select.setEnabled(true);

                setMapCenter(newAddress.getCoordinate());

                currentAddressField.setValue(newAddress);
            }
    }

    public Address getSelected() {
        return selected;
    }

    public void setSelected(Address selected) {
        this.selected = selected;

        currentAddressField.setValue(selected);

        if (selected != null) {
            setMapCenter(selected.getCoordinate());
        }
    }
}