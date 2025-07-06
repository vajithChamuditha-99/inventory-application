package com.service.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "warehouses",
        indexes = {
                @Index(name = "idx_warehouse_code", columnList = "code"),
                @Index(name = "idx_warehouse_city", columnList = "city")
        })
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(unique = true, nullable = false, length = 20)
    @NotBlank(message = "Warehouse code is required")
    @Size(max = 20, message = "Warehouse code must not exceed 20 characters")
    private String code;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Warehouse name is required")
    @Size(max = 100, message = "Warehouse name must not exceed 100 characters")
    private String name;

    @Column(length = 200)
    @Size(max = 200, message = "Address must not exceed 200 characters")
    private String address;

    @Column(length = 50)
    @Size(max = 50, message = "City must not exceed 50 characters")
    private String city;

    @Column(length = 50)
    @Size(max = 50, message = "State must not exceed 50 characters")
    private String state;

    @Column(length = 20)
    @Size(max = 20, message = "Postal code must not exceed 20 characters")
    private String postalCode;

    @Column(length = 50)
    @Size(max = 50, message = "Country must not exceed 50 characters")
    private String country;

    @Column(length = 20)
    @Size(max = 20, message = "Phone must not exceed 20 characters")
    private String phone;

    @Column(length = 100)
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Email(message = "Invalid email format")
    private String email;

    @Column(length = 50)
    @Size(max = 50, message = "Manager name must not exceed 50 characters")
    private String managerName;

    @Column(nullable = false)
    @NotNull(message = "Active status is required")
    private Boolean isActive;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}
