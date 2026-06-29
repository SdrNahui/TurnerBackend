package com.example.turner.DTO;

import com.example.turner.Model.AvailabilityBlock;

import java.time.LocalDateTime;

public class AvailabilityResponse {
    private long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public AvailabilityResponse(AvailabilityBlock availabilityBlock){
        this.id = availabilityBlock.getId();
        this.startDate = availabilityBlock.getStartDate();
        this.endDate = availabilityBlock.getEndDate();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
