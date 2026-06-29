package com.example.turner.DTO;

import java.time.LocalDateTime;

public class AppointmentRequest {
    private Long offeredId;
    private LocalDateTime startDate;
    private boolean hasAtHome;


    public AppointmentRequest(){

    }

    public boolean isHasAtHome() {
        return hasAtHome;
    }

    public void setHasAtHome(boolean hasAtHome) {
        this.hasAtHome = hasAtHome;
    }

    public Long getOfferedId() {
        return offeredId;
    }

    public void setOfferedId(Long offeredId) {
        this.offeredId = offeredId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }


}
