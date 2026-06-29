package com.example.turner.Service;

import com.example.turner.DTO.AffectedAppointment;
import com.example.turner.DTO.AvailabilityUpdateResult;
import com.example.turner.DTO.TimeRequest;
import com.example.turner.Model.*;
import com.example.turner.Repository.AppointmentRepository;
import com.example.turner.Repository.AvailabilityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    private final AppointmentRepository appointmentRepository;

    public AvailabilityService(AvailabilityRepository availabilityRepository,
                               AppointmentRepository appointmentRepository) {
        this.availabilityRepository = availabilityRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public AvailabilityBlock createAvailability(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new IllegalStateException("Rango invalido");
        }
        boolean overlap = availabilityRepository.existsByActiveTrueAndStartDateLessThanAndEndDateGreaterThan
                (end, start);
        if (overlap) {
            throw new IllegalStateException("Superposicion de disponibilidad");
        }
        if(start.getDayOfWeek() == DayOfWeek.SUNDAY ||end.getDayOfWeek() == DayOfWeek.SUNDAY){
            throw new IllegalStateException("No se trabaja el domingo");
        }
        AvailabilityBlock a1 = new AvailabilityBlock();
        a1.setStartDate(start);
        a1.setEndDate(end);
        return availabilityRepository.save(a1);
    }

    public List<AvailabilityBlock> getAllAvailability() {
        return availabilityRepository.findByActiveTrue();
    }

    public Optional<AvailabilityBlock> getAvailabilityById(Long id) {
        return availabilityRepository.findById(id);
    }

    @Transactional
    public AvailabilityUpdateResult updateAvailability(Long id, TimeRequest timeRequest) {
        AvailabilityBlock exist = availabilityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());

        if (!timeRequest.getStart().isBefore(timeRequest.getEnd())) {
            throw new IllegalArgumentException("Rango invalido");
        }
        boolean overlap = availabilityRepository.existsByActiveTrueAndIdNotAndStartDateLessThanAndEndDateGreaterThan(id,
                timeRequest.getEnd(), timeRequest.getStart());
        if (overlap) {
            throw new IllegalStateException("Superposicion de disponibilidad");
        }
        List<Appointment> reservedAppointments = appointmentRepository
                .findByStatusAndStartDateLessThanAndEndDateGreaterThan(AppointmentStatus.RESERVED, exist.getEndDate(),
                        exist.getStartDate());
        List<AffectedAppointment> affected = new ArrayList<>();
        for (Appointment a : reservedAppointments) {
            if (a.getStartDate().isBefore(timeRequest.getStart()) || a.getEndDate().isAfter(timeRequest.getEnd())) {
                AffectedAppointment dto = new AffectedAppointment();
                dto.setAppointmentId(a.getId());
                dto.setStartDate(a.getStartDate());
                dto.setEndDate(a.getEndDate());
                dto.setClientId(a.getClient().getId());
                dto.setClientName(a.getClient().getName());
                dto.setClientType(a.getClient().getType());
                dto.setOfferedName(a.getOffered().getServiceName());
                affected.add(dto);
            }
        }

        AvailabilityUpdateResult result = new AvailabilityUpdateResult();
        result.setAvailabilityId(exist.getId());
        result.setStartDate(timeRequest.getStart());
        result.setEndDate(timeRequest.getEnd());
        result.setAffectedAppointment(affected);
        result.setHasConflict(!affected.isEmpty());
        if (result.isHasConflict()) {
            return result;
        }

        exist.setStartDate(timeRequest.getStart());
        exist.setEndDate(timeRequest.getEnd());

        return result;
    }
    @Transactional
    public void deleteAvailability(Long id) {
        AvailabilityBlock block = availabilityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Disponibilidad no encontrada"));
        block.setActive(false);
        availabilityRepository.save(block);
    }
    @Transactional
    public AvailabilityUpdateResult forceUpdateAvailability(Long id, TimeRequest timeRequest) {
        AvailabilityBlock exist = availabilityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
        exist.setStartDate(timeRequest.getStart());
        exist.setEndDate(timeRequest.getEnd());
        availabilityRepository.save(exist);
        AvailabilityUpdateResult result = new AvailabilityUpdateResult();
        result.setAvailabilityId(exist.getId());
        result.setStartDate(timeRequest.getStart());
        result.setEndDate(timeRequest.getEnd());
        result.setHasConflict(false);
        return result;
    }
}
