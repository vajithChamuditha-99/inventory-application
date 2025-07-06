package com.service.inventory.mapper;

import com.service.inventory.dto.WarehouseDto;
import com.service.inventory.entity.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WarehouseMapper {

    WarehouseDto toDto(Warehouse warehouse);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Warehouse toEntity(WarehouseDto warehouseDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(@MappingTarget Warehouse warehouse, WarehouseDto warehouseDto);
}
