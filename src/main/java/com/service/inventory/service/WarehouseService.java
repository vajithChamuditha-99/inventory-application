package com.service.inventory.service;

import com.service.inventory.dto.WarehouseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface WarehouseService {

    WarehouseDto createWarehouse(WarehouseDto warehouseDto);

    WarehouseDto getWarehouseById(UUID id);

    WarehouseDto getWarehouseByCode(String code);

    List<WarehouseDto> getAllWarehouses();

    Page<WarehouseDto> getAllWarehouses(Pageable pageable);

    List<WarehouseDto> getActiveWarehouses();

    Page<WarehouseDto> getActiveWarehouses(Pageable pageable);

    WarehouseDto updateWarehouse(UUID id, WarehouseDto warehouseDto);

    void deleteWarehouse(UUID id);

    void softDeleteWarehouse(UUID id);

    List<WarehouseDto> getWarehousesByCity(String city);

    List<WarehouseDto> getWarehousesByState(String state);

    List<WarehouseDto> getWarehousesByCountry(String country);

    Page<WarehouseDto> searchWarehouses(String code, String name, String city,
                                        String state, String country, Boolean isActive,
                                        Pageable pageable);

    boolean existsByCode(String code);
}