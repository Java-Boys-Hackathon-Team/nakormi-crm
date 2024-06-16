package ru.javaboys.nakormi.security;

import io.jmix.core.entity.KeyValueEntity;
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

@ResourceRole(name = "Volunteer", code = VolunteerRole.CODE)
public interface VolunteerRole {
    String CODE = "volunteer";

    @EntityAttributePolicy(entityClass = Address.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Address.class, actions = EntityPolicyAction.READ)
    void address();

    @EntityAttributePolicy(entityClass = Animal.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Animal.class, actions = EntityPolicyAction.READ)
    void animal();

    @EntityAttributePolicy(entityClass = AnimalAttachment.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = AnimalAttachment.class, actions = EntityPolicyAction.READ)
    void animalAttachment();

    @EntityPolicy(entityClass = Attachment.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.CREATE})
    @EntityAttributePolicy(entityClass = Attachment.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void attachment();

    @EntityPolicy(entityClass = AnimalVolunteer.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityClass = AnimalVolunteer.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void animalVolunteer();

    @EntityAttributePolicy(entityClass = District.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = District.class, actions = EntityPolicyAction.READ)
    void district();

    @EntityAttributePolicy(entityClass = FoodCategory.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = FoodCategory.class, actions = EntityPolicyAction.READ)
    void foodCategory();

    @EntityAttributePolicy(entityClass = Food.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Food.class, actions = EntityPolicyAction.READ)
    void food();

    @EntityPolicy(entityClass = FoodTransferAttachment.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.CREATE})
    @EntityAttributePolicy(entityClass = FoodTransferAttachment.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void foodTransferAttachment();

    @EntityAttributePolicy(entityClass = FoodTransferRow.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = FoodTransferRow.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.CREATE})
    void foodTransferRow();

    @EntityAttributePolicy(entityClass = Person.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Person.class, actions = EntityPolicyAction.READ)
    void person();

    @EntityAttributePolicy(entityClass = Pride.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Pride.class, actions = EntityPolicyAction.READ)
    void pride();

    @EntityAttributePolicy(entityClass = ProductMovement.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = ProductMovement.class, actions = EntityPolicyAction.ALL)
    void productMovement();

    @EntityAttributePolicy(entityClass = ProductMovementRow.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = ProductMovementRow.class, actions = EntityPolicyAction.ALL)
    void productMovementRow();

    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityClass = User.class, attributes = {"telegramUser", "person", "version", "username", "firstName", "lastName", "email", "active", "timeZoneId"}, action = EntityAttributePolicyAction.VIEW)
    void user();

    @EntityAttributePolicy(entityClass = PuckUpOrder.class, attributes = "status", action = EntityAttributePolicyAction.MODIFY)
    @EntityAttributePolicy(entityClass = PuckUpOrder.class, attributes = {"id", "date", "number", "creator", "volunteer", "warehouse", "numberFormatted"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = PuckUpOrder.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void puckUpOrder();

    @EntityAttributePolicy(entityClass = Volunteer.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Volunteer.class, actions = EntityPolicyAction.READ)
    void volunteer();

    @EntityAttributePolicy(entityClass = VolunteerAttachment.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = VolunteerAttachment.class, actions = EntityPolicyAction.READ)
    void volunteerAttachment();

    @EntityAttributePolicy(entityClass = Warehouse.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Warehouse.class, actions = EntityPolicyAction.READ)
    void warehouse();

    @MenuPolicy(menuIds = {"ProductMovement.list", "VolunteerRemainderView", "VolunteerAnimalsView", "VolunteerOrderView", "TopVolunteerView", "Volunteerstatistic"})
    @ViewPolicy(viewIds = {"LoginView", "MainView", "ProductMovement.list", "ProductMovement.detail", "ProductMovementRow.detail", "ProductMovementRow.list", "FoodSelect.list", "WarehouseSelect.list", "VolunteerSelect.list", "PersonSelect.list", "VolunteerRemainderView", "VolunteerAnimalsView", "VolunteerOrderView", "SuccessScreen", "TopVolunteerView", "Animal.detail", "Volunteerstatistic"})
    void screens();

    @SpecificPolicy(resources = "ui.loginToUi")
    void specific();

    @EntityPolicy(entityClass = FoodTransfer.class, actions = EntityPolicyAction.CREATE)
    void foodTransfer();

    @EntityAttributePolicy(entityClass = KeyValueEntity.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = KeyValueEntity.class, actions = EntityPolicyAction.READ)
    void keyValueEntity();
}