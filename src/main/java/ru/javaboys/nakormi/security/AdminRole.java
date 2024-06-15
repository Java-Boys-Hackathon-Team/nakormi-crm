package ru.javaboys.nakormi.security;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;
import ru.javaboys.nakormi.dto.ProductMovement;
import ru.javaboys.nakormi.dto.ProductMovementRow;
import ru.javaboys.nakormi.entity.Address;
import ru.javaboys.nakormi.entity.Animal;
import ru.javaboys.nakormi.entity.AnimalAttachment;
import ru.javaboys.nakormi.entity.AnimalVolunteer;
import ru.javaboys.nakormi.entity.Attachment;
import ru.javaboys.nakormi.entity.District;
import ru.javaboys.nakormi.entity.Food;
import ru.javaboys.nakormi.entity.FoodCategory;
import ru.javaboys.nakormi.entity.FoodTransfer;
import ru.javaboys.nakormi.entity.FoodTransferAttachment;
import ru.javaboys.nakormi.entity.FoodTransferRow;
import ru.javaboys.nakormi.entity.Person;
import ru.javaboys.nakormi.entity.Pride;
import ru.javaboys.nakormi.entity.PuckUpOrder;
import ru.javaboys.nakormi.entity.User;
import ru.javaboys.nakormi.entity.Volunteer;
import ru.javaboys.nakormi.entity.VolunteerAttachment;
import ru.javaboys.nakormi.entity.Warehouse;

@ResourceRole(name = "Admin", code = AdminRole.CODE)
public interface AdminRole {
    String CODE = "admin";

    @EntityAttributePolicy(entityClass = Address.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Address.class, actions = EntityPolicyAction.ALL)
    void address();

    @EntityAttributePolicy(entityClass = Animal.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Animal.class, actions = EntityPolicyAction.ALL)
    void animal();

    @EntityAttributePolicy(entityClass = AnimalAttachment.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = AnimalAttachment.class, actions = EntityPolicyAction.ALL)
    void animalAttachment();

    @EntityAttributePolicy(entityClass = AnimalVolunteer.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = AnimalVolunteer.class, actions = EntityPolicyAction.ALL)
    void animalVolunteer();

    @EntityAttributePolicy(entityClass = District.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = District.class, actions = EntityPolicyAction.ALL)
    void district();

    @EntityAttributePolicy(entityClass = Food.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Food.class, actions = EntityPolicyAction.ALL)
    void food();

    @EntityAttributePolicy(entityClass = Attachment.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Attachment.class, actions = EntityPolicyAction.ALL)
    void attachment();

    @EntityAttributePolicy(entityClass = FoodCategory.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = FoodCategory.class, actions = EntityPolicyAction.ALL)
    void foodCategory();

    @EntityAttributePolicy(entityClass = FoodTransferAttachment.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = FoodTransferAttachment.class, actions = EntityPolicyAction.ALL)
    void foodTransferAttachment();

    @EntityAttributePolicy(entityClass = Person.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Person.class, actions = EntityPolicyAction.ALL)
    void person();

    @EntityAttributePolicy(entityClass = FoodTransfer.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = FoodTransfer.class, actions = EntityPolicyAction.ALL)
    void foodTransfer();

    @EntityAttributePolicy(entityClass = FoodTransferRow.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = FoodTransferRow.class, actions = EntityPolicyAction.ALL)
    void foodTransferRow();

    @EntityAttributePolicy(entityClass = ProductMovement.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = ProductMovement.class, actions = EntityPolicyAction.ALL)
    void productMovement();

    @EntityAttributePolicy(entityClass = ProductMovementRow.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = ProductMovementRow.class, actions = EntityPolicyAction.ALL)
    void productMovementRow();

    @EntityAttributePolicy(entityClass = Pride.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Pride.class, actions = EntityPolicyAction.ALL)
    void pride();

    @EntityAttributePolicy(entityClass = Volunteer.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Volunteer.class, actions = EntityPolicyAction.ALL)
    void volunteer();

    @EntityAttributePolicy(entityClass = PuckUpOrder.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = PuckUpOrder.class, actions = EntityPolicyAction.ALL)
    void puckUpOrder();

    @EntityAttributePolicy(entityClass = Warehouse.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Warehouse.class, actions = EntityPolicyAction.ALL)
    void warehouse();

    @EntityAttributePolicy(entityClass = VolunteerAttachment.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = VolunteerAttachment.class, actions = EntityPolicyAction.ALL)
    void volunteerAttachment();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.ALL)
    void user();

    @MenuPolicy(menuIds = {"ProductMovement.list", "TopVolunteerView", "User.list", "District.list", "Address.list", "Person.list", "Warehouse.list", "Volunteer.list", "AddressLookupView", "Animal.list", "Pride.list", "Food.list", "FoodCategory.list", "PuckUpOrder.list", "FoodTransfer.list", "InvitationCode.list", "TelegamUser.list", "report_Report.list", "report_ReportGroup.list", "report_ReportRunView", "report_ReportTableView", "sec_ResourceRoleModel.list", "sec_RowLevelRoleModel.list", "datatl_entityInspectorListView"})
    @ViewPolicy(viewIds = {"ProductMovement.list", "TopVolunteerView", "User.list", "District.list", "Address.list", "Person.list", "Warehouse.list", "Volunteer.list", "AddressLookupView", "Animal.list", "Pride.list", "Food.list", "FoodCategory.list", "PuckUpOrder.list", "FoodTransfer.list", "InvitationCode.list", "TelegamUser.list", "report_Report.list", "report_ReportGroup.list", "report_ReportRunView", "report_ReportTableView", "sec_ResourceRoleModel.list", "sec_RowLevelRoleModel.list", "datatl_entityInspectorListView", "LoginView", "User.detail", "MainView", "entityInfoView", "datatl_entityInspectorDetailView", "flowui_PropertyFilterCondition.detail", "flowui_JpqlFilterCondition.detail", "flowui_AddConditionView", "flowui_GroupFilterCondition.detail", "headerPropertyFilterLayout", "inputDialog", "multiValueSelectDialog", "sec_ResourceRoleModel.detail", "sec_ResourceRoleModel.lookup", "sec_RowLevelRoleModel.detail", "sec_RowLevelRoleModel.lookup", "resetPasswordView", "changePasswordView", "sec_EntityAttributeResourcePolicyModel.detail", "sec_EntityResourcePolicyModel.detail", "sec_ViewResourcePolicyModel.detail", "sec_GraphQLResourcePolicyModel.detail", "sec_MenuResourcePolicyModel.detail", "sec_ViewResourcePolicyModel.create", "sec_MenuResourcePolicyModel.create", "sec_ResourcePolicyModel.detail", "sec_EntityAttributeResourcePolicyModel.create", "sec_SpecificResourcePolicyModel.detail", "sec_EntityResourcePolicyModel.create", "roleAssignmentView", "sec_RowLevelPolicyModel.detail", "sec_UserSubstitution.detail", "sec_UserSubstitution.view", "FoodCategory.detail", "Volunteer.detail", "Address.detail", "Person.detail", "District.detail", "Warehouse.detail", "Food.detail", "FoodTransfer.detail", "FoodTransferRow.detail", "FoodTransferRow.list", "report_InputParametersDialogView", "report_ReportGroup.detail", "report_WizardReportRegion.detail", "report_Report.detail", "report_ReportExecution.list", "report_ReportExecutionDialogView", "report_ReportTemplate.detail", "report_ReportInputParameter.detail", "report_ReportImportDialogView", "report_ReportWizardCreatorView", "report_ScriptEditorView", "report_EntityTreeNode.list", "report_QueryParameter.detail", "report_ReportValueFormat.detail", "Attachment.list", "ProductMovement.detail", "ProductMovementRow.detail", "ProductMovementRow.list", "PuckUpOrder.detail", "Pride.detail", "Animal.detail", "FoodSelect.list", "PersonSelect.list", "VolunteerSelect.list", "WarehouseSelect.list", "TelegamUser.detail"})
    void screens();

    @SpecificPolicy(resources = "ui.loginToUi")
    void specific();
}