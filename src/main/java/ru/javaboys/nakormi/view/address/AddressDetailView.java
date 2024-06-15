package ru.javaboys.nakormi.view.address;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsflowui.component.event.MapClickEvent;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import ru.javaboys.nakormi.entity.Address;
import ru.javaboys.nakormi.view.main.MainView;

@Route(value = "addresses/:id", layout = MainView.class)
@ViewController("Address.detail")
@ViewDescriptor("address-detail-view.xml")
@EditedEntityContainer("addressDc")
public class AddressDetailView extends StandardDetailView<Address> {

    protected GeometryFactory geometryFactory = GeometryUtils.getGeometryFactory();

    @Subscribe("map")
    public void onMapMapClick(final MapClickEvent event) {
        Point point = geometryFactory.createPoint(event.getCoordinate());
        Address address = getEditedEntity();
        address.setCoordinate(point);
    }
}