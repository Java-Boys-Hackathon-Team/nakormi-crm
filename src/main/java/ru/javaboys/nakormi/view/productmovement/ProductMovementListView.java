package ru.javaboys.nakormi.view.productmovement;

import com.vaadin.flow.router.Route;
import io.jmix.core.LoadContext;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.Target;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.dto.ProductMovement;
import ru.javaboys.nakormi.view.main.MainView;

import java.util.Collection;
import java.util.List;

@Route(value = "productMovements", layout = MainView.class)
@ViewController("ProductMovement.list")
@ViewDescriptor("product-movement-list-view.xml")
@LookupComponent("productMovementsDataGrid")
@DialogMode(width = "50em")
public class ProductMovementListView extends StandardListView<ProductMovement> {

    @Install(to = "productMovementsDl", target = Target.DATA_LOADER)
    protected List<ProductMovement> productMovementsDlLoadDelegate(LoadContext<ProductMovement> loadContext) {
        // Here you can load entities from an external storage.
        // Set the loaded entities to the not-new state using EntityStates.setNew(entity, false).
        return List.of();
    }

    @Install(to = "productMovementsDataGrid.remove", subject = "delegate")
    private void productMovementsDataGridRemoveDelegate(final Collection<ProductMovement> collection) {
        for (ProductMovement entity : collection) {
            // Here you can remove entities from an external storage
        }
    }
}
