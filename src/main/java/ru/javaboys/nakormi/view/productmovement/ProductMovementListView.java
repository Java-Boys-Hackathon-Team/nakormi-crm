package ru.javaboys.nakormi.view.productmovement;

import com.vaadin.flow.router.Route;
import io.jmix.core.LoadContext;
import io.jmix.flowui.Actions;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    @ViewComponent
    private DataGrid<ProductMovement> productMovementsDataGrid;
    @ViewComponent
    private JmixButton editBtn;
    @ViewComponent
    private JmixButton removeBtn;
    @Autowired
    private Actions actions;
    @ViewComponent
    private JmixButton createBtn;

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

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        productMovementsDataGrid.setVisible(false);
        editBtn.setVisible(false);
        removeBtn.setVisible(false);
        createBtn.click();
    }
}
