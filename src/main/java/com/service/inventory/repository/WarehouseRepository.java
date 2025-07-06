package com.service.inventory.repository;

import com.service.inventory.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {

    Optional<Warehouse> findByCode(String code);

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, UUID id);

    List<Warehouse> findByIsActiveTrue();

    Page<Warehouse> findByIsActiveTrue(Pageable pageable);

    List<Warehouse> findByCity(String city);

    List<Warehouse> findByState(String state);

    List<Warehouse> findByCountry(String country);

    @Query("SELECT w FROM Warehouse w WHERE " +
            "(:code IS NULL OR LOWER(w.code) LIKE LOWER(CONCAT('%', :code, '%'))) AND " +
            "(:name IS NULL OR LOWER(w.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:city IS NULL OR LOWER(w.city) LIKE LOWER(CONCAT('%', :city, '%'))) AND " +
            "(:state IS NULL OR LOWER(w.state) LIKE LOWER(CONCAT('%', :state, '%'))) AND " +
            "(:country IS NULL OR LOWER(w.country) LIKE LOWER(CONCAT('%', :country, '%'))) AND " +
            "(:isActive IS NULL OR w.isActive = :isActive)")
    Page<Warehouse> findWithFilters(@Param("code") String code,
                                    @Param("name") String name,
                                    @Param("city") String city,
                                    @Param("state") String state,
                                    @Param("country") String country,
                                    @Param("isActive") Boolean isActive,
                                    Pageable pageable);
}