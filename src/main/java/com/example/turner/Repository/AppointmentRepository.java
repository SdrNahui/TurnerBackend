package com.example.turner.Repository;

import com.example.turner.Model.Appointment;
import com.example.turner.Model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
    boolean existsByStatusAndStartDateLessThanAndEndDateGreaterThan(AppointmentStatus status, LocalDateTime end,
                                                                 LocalDateTime start);
    List<Appointment> findByStatusAndStartDateLessThanAndEndDateGreaterThan(AppointmentStatus status,
                                                                            LocalDateTime end, LocalDateTime start);
    boolean existsByIdNotAndStatusAndStartDateLessThanAndEndDateGreaterThan(Long id, AppointmentStatus status,
                                                                         LocalDateTime end, LocalDateTime start);
    List<Appointment> findByStatusAndStartDateBetween(AppointmentStatus status, LocalDateTime start, LocalDateTime end);
}
