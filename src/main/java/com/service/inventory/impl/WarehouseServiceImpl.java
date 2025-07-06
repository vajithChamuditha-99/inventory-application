package com.service.inventory.impl;

import com.service.inventory.dto.WarehouseDto;
import com.service.inventory.entity.Warehouse;
import com.service.inventory.exception.ResourceNotFoundException;
import com.service.inventory.exception.DuplicateResourceException;
import com.service.inventory.mapper.WarehouseMapper;
import com.service.inventory.repository.WarehouseRepository;
import com.service.inventory.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    @Override
    public WarehouseDto createWarehouse(WarehouseDto warehouseDto) {
        log.info("Creating warehouse with code: {}", warehouseDto.getCode());

        if (warehouseRepository.existsByCode(warehouseDto.getCode())) {
            throw new DuplicateResourceException("Warehouse with code '" + warehouseDto.getCode() + "' already exists");
        }

        Warehouse warehouse = warehouseMapper.toEntity(warehouseDto);
        warehouse = warehouseRepository.save(warehouse);

        log.info("Warehouse created successfully with ID: {}", warehouse.getId());
        return warehouseMapper.toDto(warehouse);
    }

    @Override
    @Transactional(readOnly = true)
    public WarehouseDto getWarehouseById(UUID id) {
        log.info("Fetching warehouse with ID: {}", id);

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with ID: " + id));

        return warehouseMapper.toDto(warehouse);
    }

    @Override
    @Transactional(readOnly = true)
    public WarehouseDto getWarehouseByCode(String code) {
        log.info("Fetching warehouse with code: {}", code);

        Warehouse warehouse = warehouseRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with code: " + code));

        return warehouseMapper.toDto(warehouse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WarehouseDto> getAllWarehouses() {
        log.info("Fetching all warehouses");

        return warehouseRepository.findAll().stream()
                .map(warehouseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WarehouseDto> getAllWarehouses(Pageable pageable) {
        log.info("Fetching all warehouses with pagination");

        return warehouseRepository.findAll(pageable)
                .map(warehouseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WarehouseDto> getActiveWarehouses() {
        log.info("Fetching active warehouses");

        return warehouseRepository.findByIsActiveTrue().stream()
                .map(warehouseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WarehouseDto> getActiveWarehouses(Pageable pageable) {
        log.info("Fetching active warehouses with pagination");

        return warehouseRepository.findByIsActiveTrue(pageable)
                .map(warehouseMapper::toDto);
    }

    @Override
    public WarehouseDto updateWarehouse(UUID id, WarehouseDto warehouseDto) {
        log.info("Updating warehouse with ID: {}", id);

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with ID: " + id));

        warehouseMapper.updateEntity(warehouse, warehouseDto);
        warehouse = warehouseRepository.save(warehouse);

        log.info("Warehouse updated successfully with ID: {}", id);
        return warehouseMapper.toDto(warehouse);
    }

    @Override
    public void deleteWarehouse(UUID id) {
        log.info("Deleting warehouse with ID: {}", id);

        if (!warehouseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Warehouse not found with ID: " + id);
        }

        warehouseRepository.deleteById(id);
        log.info("Warehouse deleted successfully with ID: {}", id);
    }

    @Override
    public void softDeleteWarehouse(UUID id) {
        log.info("Soft deleting warehouse with ID: {}", id);

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with ID: " + id));

        warehouse.setIsActive(false);
        warehouseRepository.save(warehouse);

        log.info("Warehouse soft deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WarehouseDto> getWarehousesByCity(String city) {
        log.info("Fetching warehouses by city: {}", city);

        return warehouseRepository.findByCity(city).stream()
                .map(warehouseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WarehouseDto> getWarehousesByState(String state) {
        log.info("Fetching warehouses by state: {}", state);

        return warehouseRepository.findByState(state).stream()
                .map(warehouseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WarehouseDto> getWarehousesByCountry(String country) {
        log.info("Fetching warehouses by country: {}", country);

        return warehouseRepository.findByCountry(country).stream()
                .map(warehouseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WarehouseDto> searchWarehouses(String code, String name, String city,
                                               String state, String country, Boolean isActive,
                                               Pageable pageable) {
        log.info("Searching warehouses with filters");

        return warehouseRepository.findWithFilters(code, name, city, state, country, isActive, pageable)
                .map(warehouseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCode(String code) {
        return warehouseRepository.existsByCode(code);
    }
}
