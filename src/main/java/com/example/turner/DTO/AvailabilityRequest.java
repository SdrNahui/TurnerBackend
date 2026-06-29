package com.example.turner.DTO;

import java.time.LocalDateTime;

public class AvailabilityRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public AvailabilityRequest(){

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
