package com.example.turner.DTO;

import jakarta.validation.constraints.NotBlank;

public class CreateAppointmentRequest {
    private Long clientId;
    private String clientName;
    private String clientLastName;
    private String phoneNumber;
    private Long offeredId;
    public  CreateAppointmentRequest(){

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

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getOfferedId() {
        return offeredId;
    }

    public void setOfferedId(Long offeredId) {
        this.offeredId = offeredId;
    }
}
