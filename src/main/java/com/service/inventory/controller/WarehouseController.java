package com.service.inventory.controller;

import com.service.inventory.dto.WarehouseDto;
import com.service.inventory.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/warehouses")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Warehouse Management", description = "APIs for managing warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    @Operation(summary = "Create a new warehouse", description = "Creates a new warehouse with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Warehouse created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Warehouse with code already exists")
    })
    public ResponseEntity<WarehouseDto> createWarehouse(@Valid @RequestBody WarehouseDto createDto) {
        log.info("Creating warehouse with code: {}", createDto.getCode());
        WarehouseDto warehouse = warehouseService.createWarehouse(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(warehouse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get warehouse by ID", description = "Retrieves a warehouse by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouse found"),
            @ApiResponse(responseCode = "404", description = "Warehouse not found")
    })
    public ResponseEntity<WarehouseDto> getWarehouseById(@PathVariable UUID id) {
        log.info("Fetching warehouse with ID: {}", id);
        WarehouseDto warehouse = warehouseService.getWarehouseById(id);
        return ResponseEntity.ok(warehouse);
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get warehouse by code", description = "Retrieves a warehouse by its code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouse found"),
            @ApiResponse(responseCode = "404", description = "Warehouse not found")
    })
    public ResponseEntity<WarehouseDto> getWarehouseByCode(@PathVariable String code) {
        log.info("Fetching warehouse with code: {}", code);
        WarehouseDto warehouse = warehouseService.getWarehouseByCode(code);
        return ResponseEntity.ok(warehouse);
    }

    @GetMapping
    @Operation(summary = "Get all warehouses", description = "Retrieves all warehouses with pagination support")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouses retrieved successfully")
    })
    public ResponseEntity<Page<WarehouseDto>> getAllWarehouses(
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all warehouses with pagination");
        Page<WarehouseDto> warehouses = warehouseService.getAllWarehouses(pageable);
        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/active")
    @Operation(summary = "Get active warehouses", description = "Retrieves only active warehouses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Active warehouses retrieved successfully")
    })
    public ResponseEntity<List<WarehouseDto>> getActiveWarehouses() {
        log.info("Fetching active warehouses");
        List<WarehouseDto> warehouses = warehouseService.getActiveWarehouses();
        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/active/pageable")
    @Operation(summary = "Get active warehouses with pagination", description = "Retrieves active warehouses with pagination support")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Active warehouses retrieved successfully")
    })
    public ResponseEntity<Page<WarehouseDto>> getActiveWarehouses(
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching active warehouses with pagination");
        Page<WarehouseDto> warehouses = warehouseService.getActiveWarehouses(pageable);
        return ResponseEntity.ok(warehouses);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update warehouse", description = "Updates an existing warehouse")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouse updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Warehouse not found")
    })
    public ResponseEntity<WarehouseDto> updateWarehouse(@PathVariable UUID id,
                                                        @Valid @RequestBody WarehouseDto updateDto) {
        log.info("Updating warehouse with ID: {}", id);
        WarehouseDto warehouse = warehouseService.updateWarehouse(id, updateDto);
        return ResponseEntity.ok(warehouse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete warehouse", description = "Permanently deletes a warehouse")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Warehouse deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Warehouse not found")
    })
    public ResponseEntity<Void> deleteWarehouse(@PathVariable UUID id) {
        log.info("Deleting warehouse with ID: {}", id);
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/soft-delete")
    @Operation(summary = "Soft delete warehouse", description = "Marks a warehouse as inactive")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Warehouse soft deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Warehouse not found")
    })
    public ResponseEntity<Void> softDeleteWarehouse(@PathVariable UUID id) {
        log.info("Soft deleting warehouse with ID: {}", id);
        warehouseService.softDeleteWarehouse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search warehouses", description = "Searches warehouses with various filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search results retrieved successfully")
    })
    public ResponseEntity<Page<WarehouseDto>> searchWarehouses(
            @Parameter(description = "Warehouse code filter") @RequestParam(required = false) String code,
            @Parameter(description = "Warehouse name filter") @RequestParam(required = false) String name,
            @Parameter(description = "City filter") @RequestParam(required = false) String city,
            @Parameter(description = "State filter") @RequestParam(required = false) String state,
            @Parameter(description = "Country filter") @RequestParam(required = false) String country,
            @Parameter(description = "Active status filter") @RequestParam(required = false) Boolean isActive,
            @PageableDefault(size = 20) Pageable pageable) {

        log.info("Searching warehouses with filters");
        Page<WarehouseDto> warehouses = warehouseService.searchWarehouses(
                code, name, city, state, country, isActive, pageable);
        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/by-city/{city}")
    @Operation(summary = "Get warehouses by city", description = "Retrieves warehouses in a specific city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouses retrieved successfully")
    })
    public ResponseEntity<List<WarehouseDto>> getWarehousesByCity(@PathVariable String city) {
        log.info("Fetching warehouses by city: {}", city);
        List<WarehouseDto> warehouses = warehouseService.getWarehousesByCity(city);
        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/by-state/{state}")
    @Operation(summary = "Get warehouses by state", description = "Retrieves warehouses in a specific state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouses retrieved successfully")
    })
    public ResponseEntity<List<WarehouseDto>> getWarehousesByState(@PathVariable String state) {
        log.info("Fetching warehouses by state: {}", state);
        List<WarehouseDto> warehouses = warehouseService.getWarehousesByState(state);
        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/by-country/{country}")
    @Operation(summary = "Get warehouses by country", description = "Retrieves warehouses in a specific country")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouses retrieved successfully")
    })
    public ResponseEntity<List<WarehouseDto>> getWarehousesByCountry(@PathVariable String country) {
        log.info("Fetching warehouses by country: {}", country);
        List<WarehouseDto> warehouses = warehouseService.getWarehousesByCountry(country);
        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/exists/{code}")
    @Operation(summary = "Check if warehouse exists", description = "Checks if a warehouse with the given code exists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existence check completed")
    })
    public ResponseEntity<Boolean> existsByCode(@PathVariable String code) {
        log.info("Checking existence of warehouse with code: {}", code);
        boolean exists = warehouseService.existsByCode(code);
        return ResponseEntity.ok(exists);
    }
}
