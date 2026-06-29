package com.example.turner.DTO;

import com.example.turner.DTO.AffectedAppointment;
import com.example.turner.Model.AvailabilityBlock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AvailabilityUpdateResult {
    private Long availabilityId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    //private AvailabilityBlock availabilityBlock;
    private List<AffectedAppointment> affectedAppointment = new ArrayList<>();
    private boolean hasConflict;

    public AvailabilityUpdateResult(){

    }

    public Long getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(Long availabilityId) {
        this.availabilityId = availabilityId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    /*public AvailabilityBlock getAvailabilityBlock() {
        return availabilityBlock;
    }

    public void setAvailabilityBlock(AvailabilityBlock availabilityBlock) {
        this.availabilityBlock = availabilityBlock;
    }*/

    public List<AffectedAppointment> getAffectedAppointment() {
        return affectedAppointment;
    }

    public void setAffectedAppointment(List<AffectedAppointment> affectedAppointment) {
        this.affectedAppointment = affectedAppointment;
    }

    public boolean isHasConflict() {
        return hasConflict;
    }

    public void setHasConflict(boolean hasConflict) {
        this.hasConflict = hasConflict;
    }
}
