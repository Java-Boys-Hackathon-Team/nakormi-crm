package ru.javaboys.nakormi.repository;

import io.jmix.core.repository.JmixDataRepository;
import ru.javaboys.nakormi.entity.Warehouse;

import java.util.List;
import java.util.UUID;

public interface WarehouseRepository extends JmixDataRepository<Warehouse, UUID> {

    List<Warehouse> findAllByStorageType(String type);

}