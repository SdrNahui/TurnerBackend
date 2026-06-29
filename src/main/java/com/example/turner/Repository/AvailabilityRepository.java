package com.example.turner.Repository;

import com.example.turner.Model.AvailabilityBlock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<AvailabilityBlock, Long>{

    // ← activos solamente
    List<AvailabilityBlock> findByActiveTrue();

    // Verifica si existe disponibilidad activa que cubra el rango
    boolean existsByActiveTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LocalDateTime start, LocalDateTime end);

    // Busca bloques activos por rango de startDate
    List<AvailabilityBlock> findByActiveTrueAndStartDateBetween(
            LocalDateTime startDate, LocalDateTime endDate);

    // Busca bloques activos que se superpongan con un rango
    List<AvailabilityBlock> findByActiveTrueAndStartDateLessThanEqualAndEndDateGreaterThan(
            LocalDateTime end, LocalDateTime start);

    // Verifica superposición activa (para crear nueva disponibilidad)
    boolean existsByActiveTrueAndStartDateLessThanAndEndDateGreaterThan(
            LocalDateTime end, LocalDateTime start);
    // Verifica superposición activa excluyendo un id (para actualizar)
    boolean existsByActiveTrueAndIdNotAndStartDateLessThanAndEndDateGreaterThan(
            Long id, LocalDateTime end, LocalDateTime start);
}