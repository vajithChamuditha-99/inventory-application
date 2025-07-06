package com.service.inventory.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDto {
    private UUID id;

    @NotBlank(message = "Warehouse code is required")
    @Size(max = 20, message = "Warehouse code must not exceed 20 characters")
    private String code;

    @NotBlank(message = "Warehouse name is required")
    @Size(max = 100, message = "Warehouse name must not exceed 100 characters")
    private String name;

    @Size(max = 200, message = "Address must not exceed 200 characters")
    private String address;

    @Size(max = 50, message = "City must not exceed 50 characters")
    private String city;

    @Size(max = 50, message = "State must not exceed 50 characters")
    private String state;

    @Size(max = 20, message = "Postal code must not exceed 20 characters")
    private String postalCode;

    @Size(max = 50, message = "Country must not exceed 50 characters")
    private String country;

    @Size(max = 20, message = "Phone must not exceed 20 characters")
    private String phone;

    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Email(message = "Invalid email format")
    private String email;

    @Size(max = 50, message = "Manager name must not exceed 50 characters")
    private String managerName;

    @NotNull(message = "Active status is required")
    private Boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;
}
