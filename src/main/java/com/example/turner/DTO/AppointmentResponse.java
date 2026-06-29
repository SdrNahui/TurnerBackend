package com.example.turner.DTO;

import com.example.turner.Model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AppointmentResponse {
    private Long id;
    private String clientName;
    private String clientLastName;
    private ClientType clientType;
    private String offeredName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private AppointmentStatus status;
    private BigDecimal priceAtBooking;
    private boolean isHomeService;

    public AppointmentResponse(Appointment appointment) {
        this.id = appointment.getId();
        this.clientName = appointment.getClient().getName();
        this.clientLastName = appointment.getClient().getLastName();
        this.clientType = appointment.getClient().getType();
        this.offeredName = appointment.getOffered().getServiceName();
        this.startDate = appointment.getStartDate();
        this.endDate = appointment.getEndDate();
        this.status = appointment.getStatus();
        this.priceAtBooking = appointment.getPriceAtBooking();
        this.isHomeService = appointment.isHomeService();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
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

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public BigDecimal getPriceAtBooking() {
        return priceAtBooking;
    }

    public void setPriceAtBooking(BigDecimal priceAtBooking) {
        this.priceAtBooking = priceAtBooking;
    }

    public boolean isHomeService() {
        return isHomeService;
    }

    public void setHomeService(boolean homeService) {
        isHomeService = homeService;
    }
}
