package com.example.turner.DTO;

import jakarta.validation.constraints.NotBlank;

public class ClientRequest {
    @NotBlank
    private String clientName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String phoneNumber;

    public ClientRequest(){

    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
