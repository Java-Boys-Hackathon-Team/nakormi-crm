package ru.javaboys.nakormi.security;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
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

    @MenuPolicy(menuIds = "*")
    @ViewPolicy(viewIds = "*")
    void screens();
}