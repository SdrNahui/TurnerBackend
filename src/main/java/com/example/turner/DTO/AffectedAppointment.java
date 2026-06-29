package com.example.turner.DTO;

import com.example.turner.Model.Client;
import com.example.turner.Model.ClientType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AffectedAppointment {
    private Long appointmentId;
    private Long clientId;
    private String clientName;
    private String offeredName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ClientType clientType;

    public AffectedAppointment(){

    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getOfferedName() {
        return offeredName;
    }

    public void setOfferedName(String offeredName) {
        this.offeredName = offeredName;
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

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }
}
