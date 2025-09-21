package ru.javaboys.nakormi.security;

import io.jmix.core.entity.KeyValueEntity;
import io.jmix.flowui.entity.filter.AbstractSingleFilterCondition;
import io.jmix.flowui.entity.filter.FilterCondition;
import io.jmix.flowui.entity.filter.FilterValueComponent;
import io.jmix.flowuidata.entity.FilterConfiguration;
import io.jmix.flowuidata.entity.UserSettingsItem;
import io.jmix.reports.entity.BandDefinition;
import io.jmix.reports.entity.DataSet;
import io.jmix.reports.entity.charts.AbstractChartDescription;
import io.jmix.reports.entity.charts.ChartSeries;
import io.jmix.reports.entity.wizard.EntityTreeNode;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securitydata.entity.UserSubstitutionEntity;
import io.jmix.securityflowui.model.BaseRoleModel;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;
import io.jmix.securityflowui.view.resourcepolicy.AttributeResourceModel;
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
import ru.javaboys.nakormi.entity.InvitationCode;
import ru.javaboys.nakormi.entity.Person;
import ru.javaboys.nakormi.entity.Pride;
import ru.javaboys.nakormi.entity.PuckUpOrder;
import ru.javaboys.nakormi.entity.TelegamUser;
import ru.javaboys.nakormi.entity.User;
import ru.javaboys.nakormi.entity.Volunteer;
import ru.javaboys.nakormi.entity.VolunteerAttachment;
import ru.javaboys.nakormi.entity.Warehouse;

@ResourceRole(name = "Reader", code = ReaderRole.CODE)
public interface ReaderRole {
    String CODE = "reader";

    @MenuPolicy(menuIds = {"ProductMovement.list", "TopVolunteerView", "User.list", "District.list", "Address.list", "Person.list", "Warehouse.list", "Volunteer.list", "Animal.list", "Pride.list", "Food.list", "FoodCategory.list", "PuckUpOrder.list", "FoodTransfer.list", "InvitationCode.list", "TelegamUser.list", "report_Report.list", "report_ReportGroup.list", "report_ReportRunView", "report_ReportTableView", "sec_ResourceRoleModel.list", "sec_RowLevelRoleModel.list", "datatl_entityInspectorListView"})
    @ViewPolicy(viewIds = {"ProductMovement.list", "TopVolunteerView", "User.list", "District.list", "Address.list", "Person.list", "Warehouse.list", "Volunteer.list", "Animal.list", "Pride.list", "Food.list", "FoodCategory.list", "PuckUpOrder.list", "FoodTransfer.list", "InvitationCode.list", "TelegamUser.list", "report_Report.list", "report_ReportGroup.list", "report_ReportRunView", "report_ReportTableView", "sec_ResourceRoleModel.list", "sec_RowLevelRoleModel.list", "datatl_entityInspectorListView", "LoginView", "User.detail", "MainView", "entityInfoView", "datatl_entityInspectorDetailView", "flowui_PropertyFilterCondition.detail", "flowui_JpqlFilterCondition.detail", "flowui_AddConditionView", "flowui_GroupFilterCondition.detail", "headerPropertyFilterLayout", "inputDialog", "multiValueSelectDialog", "sec_ResourceRoleModel.detail", "sec_ResourceRoleModel.lookup", "sec_RowLevelRoleModel.detail", "sec_RowLevelRoleModel.lookup", "resetPasswordView", "changePasswordView", "sec_EntityAttributeResourcePolicyModel.detail", "sec_EntityResourcePolicyModel.detail", "sec_ViewResourcePolicyModel.detail", "sec_GraphQLResourcePolicyModel.detail", "sec_MenuResourcePolicyModel.detail", "sec_ViewResourcePolicyModel.create", "sec_MenuResourcePolicyModel.create", "sec_ResourcePolicyModel.detail", "sec_EntityAttributeResourcePolicyModel.create", "sec_SpecificResourcePolicyModel.detail", "sec_EntityResourcePolicyModel.create", "roleAssignmentView", "sec_RowLevelPolicyModel.detail", "sec_UserSubstitution.detail", "sec_UserSubstitution.view", "FoodCategory.detail", "Volunteer.detail", "Address.detail", "Person.detail", "District.detail", "Warehouse.detail", "Food.detail", "FoodTransfer.detail", "FoodTransferRow.detail", "FoodTransferRow.list", "report_InputParametersDialogView", "report_ReportGroup.detail", "report_WizardReportRegion.detail", "report_Report.detail", "report_ReportExecution.list", "report_ReportExecutionDialogView", "report_ReportTemplate.detail", "report_ReportInputParameter.detail", "report_ReportImportDialogView", "report_ReportWizardCreatorView", "report_ScriptEditorView", "report_EntityTreeNode.list", "report_QueryParameter.detail", "report_ReportValueFormat.detail", "Attachment.list", "ProductMovement.detail", "ProductMovementRow.detail", "ProductMovementRow.list", "PuckUpOrder.detail", "Pride.detail", "Animal.detail", "FoodSelect.list", "PersonSelect.list", "VolunteerSelect.list", "WarehouseSelect.list", "TelegamUser.detail"})
    void screens();

    @EntityAttributePolicy(entityClass = AbstractChartDescription.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void abstractChartDescription();

    @EntityAttributePolicy(entityClass = AbstractSingleFilterCondition.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void abstractSingleFilterCondition();

    @EntityPolicy(entityClass = Address.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityClass = Address.class, attributes = {"district", "addressText", "coordinate", "warehouse"}, action = EntityAttributePolicyAction.VIEW)
    void address();

    @EntityPolicy(entityClass = Animal.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityClass = Animal.class, attributes = {"nickname", "type", "district", "gender", "isSterilized", "description", "pride", "attachments", "volunteers"}, action = EntityAttributePolicyAction.VIEW)
    void animal();

    @EntityPolicy(entityClass = Attachment.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityClass = Attachment.class, attributes = {"telegramFileId", "name", "source"}, action = EntityAttributePolicyAction.VIEW)
    void attachment();

    @EntityPolicy(entityClass = AnimalAttachment.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityClass = AnimalAttachment.class, attributes = {"animal", "attachment"}, action = EntityAttributePolicyAction.VIEW)
    void animalAttachment();

    @EntityPolicy(entityClass = AnimalVolunteer.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityClass = AnimalVolunteer.class, attributes = {"animal", "volunteer"}, action = EntityAttributePolicyAction.VIEW)
    void animalVolunteer();

    @EntityAttributePolicy(entityClass = AttributeResourceModel.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void attributeResourceModel();

    @EntityAttributePolicy(entityClass = BandDefinition.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void bandDefinition();

    @EntityAttributePolicy(entityClass = BaseRoleModel.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void baseRoleModel();

    @EntityAttributePolicy(entityClass = ChartSeries.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void chartSeries();

    @EntityPolicy(entityClass = District.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityClass = District.class, attributes = "name", action = EntityAttributePolicyAction.VIEW)
    void district();

    @EntityAttributePolicy(entityClass = DataSet.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void dataSet();

    @EntityAttributePolicy(entityClass = FilterCondition.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void filterCondition();

    @EntityAttributePolicy(entityClass = EntityTreeNode.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void entityTreeNode();

    @EntityPolicy(entityClass = Food.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityClass = Food.class, attributes = {"category", "name", "measure", "weight"}, action = EntityAttributePolicyAction.VIEW)
    void food();

    @EntityAttributePolicy(entityClass = FilterConfiguration.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void filterConfiguration();

    @EntityAttributePolicy(entityClass = FilterValueComponent.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void filterValueComponent();

    @EntityPolicy(entityClass = Warehouse.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityClass = Warehouse.class, attributes = {"address", "description", "contacts", "storageType", "supervisor"}, action = EntityAttributePolicyAction.VIEW)
    void warehouse();

    @EntityPolicy(entityClass = VolunteerAttachment.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityClass = VolunteerAttachment.class, attributes = {"volunteer", "attachment"}, action = EntityAttributePolicyAction.VIEW)
    void volunteerAttachment();

    @EntityAttributePolicy(entityClass = UserSubstitutionEntity.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void userSubstitutionEntity();

    @EntityPolicy(entityClass = Volunteer.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityClass = Volunteer.class, attributes = {"person", "passportNumber", "warehouse", "animals", "attachments", "telegramUser"}, action = EntityAttributePolicyAction.VIEW)
    void volunteer();

    @EntityAttributePolicy(entityClass = UserSettingsItem.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void userSettingsItem();

    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityClass = User.class, attributes = {"person", "version", "username", "firstName", "lastName", "email", "active", "timeZoneId"}, action = EntityAttributePolicyAction.VIEW)
    void user();

    @EntityAttributePolicy(entityClass = FoodTransferAttachment.class, attributes = {"foodTransfer", "attachment"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = FoodTransferAttachment.class, actions = EntityPolicyAction.READ)
    void foodTransferAttachment();

    @EntityAttributePolicy(entityClass = FoodCategory.class, attributes = {"parent", "name"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = FoodCategory.class, actions = EntityPolicyAction.READ)
    void foodCategory();

    @EntityAttributePolicy(entityClass = FoodTransfer.class, attributes = {"number", "date", "volunteer", "transferType", "warehouseSource", "warehouseReceiver", "rows", "attachments", "numberFormatted"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = FoodTransfer.class, actions = EntityPolicyAction.READ)
    void foodTransfer();

    @EntityAttributePolicy(entityClass = FoodTransferRow.class, attributes = {"foodTransfer", "date", "movement", "warehouse", "food", "quantity"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = FoodTransferRow.class, actions = EntityPolicyAction.READ)
    void foodTransferRow();

    @EntityAttributePolicy(entityClass = InvitationCode.class, attributes = {"expirationDate", "code", "used", "person", "telegamUser"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = InvitationCode.class, actions = EntityPolicyAction.READ)
    void invitationCode();

    @EntityAttributePolicy(entityClass = Person.class, attributes = {"name", "surname", "phone", "address", "type", "invitationCode", "gender"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Person.class, actions = EntityPolicyAction.READ)
    void person();

    @EntityAttributePolicy(entityClass = Pride.class, attributes = {"name", "district", "animals"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Pride.class, actions = EntityPolicyAction.READ)
    void pride();

    @EntityAttributePolicy(entityClass = ProductMovement.class, attributes = {"transferType", "warehouseSource", "warehouseReceiver", "beneficiary", "volunteer", "details", "attachments"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = ProductMovement.class, actions = EntityPolicyAction.READ)
    void productMovement();

    @EntityAttributePolicy(entityClass = PuckUpOrder.class, attributes = {"date", "number", "creator", "volunteer", "warehouse", "isClosed", "numberFormatted"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = PuckUpOrder.class, actions = EntityPolicyAction.READ)
    void puckUpOrder();

    @EntityAttributePolicy(entityClass = TelegamUser.class, attributes = {"telegramUserFirstName", "invitationCode", "telegramUserLastName", "invitationCodeOk", "nakormiCrmRegistrationOk", "nakormiCrmAccountOk", "telegramUserId", "telegramChatId", "telegramUserName", "user", "volunteer"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = TelegamUser.class, actions = EntityPolicyAction.READ)
    void telegamUser();

    @EntityAttributePolicy(entityClass = ProductMovementRow.class, attributes = {"food", "amount"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = ProductMovementRow.class, actions = EntityPolicyAction.READ)
    void productMovementRow();

    @EntityAttributePolicy(entityClass = KeyValueEntity.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = KeyValueEntity.class, actions = EntityPolicyAction.READ)
    void keyValueEntity();

    @SpecificPolicy(resources = "ui.loginToUi")
    void specific();
}