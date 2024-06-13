package ru.javaboys.nakormi.view.productmovementrow;

import com.vaadin.flow.router.Route;
import io.jmix.core.LoadContext;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.Target;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import ru.javaboys.nakormi.dto.ProductMovementRow;
import ru.javaboys.nakormi.view.main.MainView;

import java.util.Collection;
import java.util.List;

@Route(value = "productMovementRows", layout = MainView.class)
@ViewController("ProductMovementRow.list")
@ViewDescriptor("product-movement-row-list-view.xml")
@LookupComponent("productMovementRowsDataGrid")
@DialogMode(width = "50em")
public class ProductMovementRowListView extends StandardListView<ProductMovementRow> {

    @Install(to = "productMovementRowsDl", target = Target.DATA_LOADER)
    protected List<ProductMovementRow> productMovementRowsDlLoadDelegate(LoadContext<ProductMovementRow> loadContext) {
        // Here you can load entities from an external storage.
        // Set the loaded entities to the not-new state using EntityStates.setNew(entity, false).
        return List.of();
    }

    @Install(to = "productMovementRowsDataGrid.remove", subject = "delegate")
    private void productMovementRowsDataGridRemoveDelegate(final Collection<ProductMovementRow> collection) {
        for (ProductMovementRow entity : collection) {
            // Here you can remove entities from an external storage
        }
    }
}
