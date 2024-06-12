package ru.javaboys.nakormi.service;

import io.jmix.core.DataManager;
import jakarta.validation.Validator;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaboys.nakormi.dto.ProductMovement;
import ru.javaboys.nakormi.dto.ProductMovementRow;
import ru.javaboys.nakormi.entity.Food;
import ru.javaboys.nakormi.entity.FoodTransfer;
import ru.javaboys.nakormi.entity.FoodTransferRow;
import ru.javaboys.nakormi.entity.MovementTypes;
import ru.javaboys.nakormi.entity.TransferTypes;
import ru.javaboys.nakormi.entity.Warehouse;
import ru.javaboys.nakormi.repository.AttachmentRepository;
import ru.javaboys.nakormi.repository.FoodTransferAttachmentRepository;
import ru.javaboys.nakormi.repository.FoodTransferRepository;
import ru.javaboys.nakormi.repository.FoodTransferRowRepository;
import ru.javaboys.nakormi.validation.FoodTransferTypes;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.javaboys.nakormi.entity.TransferTypes.*;
import static ru.javaboys.nakormi.validation.FoodTransferTypes.*;

@Service
public class MovementService {
    @Autowired private DataManager dataManager;
    @Autowired private FoodTransferAttachmentRepository foodTransferAttachmentRepository;
    @Autowired private FoodTransferRowRepository foodTransferRowRepository;
    @Autowired private FoodTransferRepository foodTransferRepository;
    @Autowired private AttachmentRepository attachmentRepository;
    @Autowired private Validator validator;


    @Transactional
    public void executeMovement(ProductMovement movement) {
        var errors = validator.validate(movement, getValidationClasses(movement.getTransferType()));
        // todo throw errors if they are

        LocalDateTime now = LocalDateTime.now();

        FoodTransfer foodTransfer = dataManager.create(FoodTransfer.class);
        foodTransfer.setDate(now);
        foodTransfer.setDescription(movement.getDescription());
//        foodTransfer.setVolunteer();
        foodTransfer.setTransferType(movement.getTransferType());
        foodTransfer.setWarehouseSource(movement.getWarehouseSource());
        foodTransfer.setWarehouseReceiver(movement.getWarehouseReceiver());

        List<FoodTransferRow> rows = new ArrayList<>();
        for (ProductMovementRow detail : movement.getDetails()) {
            switch (movement.getTransferType()) {
                case UNATTACHED_PICKUP -> {
                    rows.add(createOutcomeRow(foodTransfer, now, detail.getFood(), detail.getAmount()));
                }
                case PICKUP_FROM_POINT, TRANSFER_TO_WAREHOUSE, TRANSFER_FROM_WAREHOUSE, TRANSFER_TO_VOLUNTEER -> {
                    rows.add(createIncomeRow(foodTransfer, now, detail.getFood(), detail.getAmount()));
                    rows.add(createOutcomeRow(foodTransfer, now, detail.getFood(), detail.getAmount()));
                }
                case FEED, TRANSFER_TO_BENEFICIARY, UNATTACHED_WRITEOFF  -> {
                    rows.add(createIncomeRow(foodTransfer, now, detail.getFood(), detail.getAmount()));
                }
            }
        }

        foodTransfer.setRows(rows);
        foodTransfer.setAttachments(movement.getAttachments());

        foodTransferRepository.save(foodTransfer);

    }

    private FoodTransferRow createIncomeRow(FoodTransfer foodTransfer, LocalDateTime now, Food food, Integer amount) {
        return createRow(foodTransfer, now, food, amount, MovementTypes.INCOME, foodTransfer.getWarehouseReceiver());
    }

    private FoodTransferRow createOutcomeRow(FoodTransfer foodTransfer, LocalDateTime now, Food food, Integer amount) {
        return createRow(foodTransfer, now, food, amount, MovementTypes.OUTCOME, foodTransfer.getWarehouseSource());
    }

    private FoodTransferRow createRow(FoodTransfer foodTransfer, LocalDateTime now, Food food,
                                      Integer amount, MovementTypes mType, Warehouse warehouse) {
        FoodTransferRow transferRow = dataManager.create(FoodTransferRow.class);
        transferRow.setFoodTransfer(foodTransfer);
        transferRow.setDate(now);
        transferRow.setFood(food);
        transferRow.setQuantity(amount);
        transferRow.setMovement(mType);
        transferRow.setWarehouse(warehouse);
        return transferRow;
    }

    private static Class<?>[] getValidationClasses(TransferTypes type) {
        Class<?> aClass = VALIDATION_TYPE_MAP.get(type);
        if (aClass == null) {
            return new Class[] {Default.class};
        }
        return new Class[] {aClass, Default.class};
    }

    private static final Map<TransferTypes, Class<?>> VALIDATION_TYPE_MAP = Map.of(
            UNATTACHED_PICKUP,       UnattachedPickup.class,
            PICKUP_FROM_POINT,       PickupFromPoint.class,
            TRANSFER_TO_WAREHOUSE,   TransferToWarehouse.class,
            TRANSFER_FROM_WAREHOUSE, TransferFromWarehouse.class,
            TRANSFER_TO_VOLUNTEER,   TransferToVolunteer.class,
            FEED,                    Feed.class,
            TRANSFER_TO_BENEFICIARY, TransferToBeneficiary.class,
            UNATTACHED_WRITEOFF,     UnattachedWriteoff.class
    );
}
