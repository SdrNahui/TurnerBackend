package com.example.turner.Model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Turno")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer startMinutes;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    @ManyToOne
    @JoinColumn(name = "Cliente_id", nullable = false)
    private Client client;
    @ManyToOne
    @JoinColumn(name = "Masaje_id", nullable = false)
    private OfferedService offered;
    private BigDecimal priceAtBooking;
    private BigDecimal commissionPercentage;
    private boolean isHomeService;

    public Appointment(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getStartMinutes() {
        return startMinutes;
    }

    public void setStartMinutes(Integer startMinutes) {
        this.startMinutes = startMinutes;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public OfferedService getOffered() {
        return offered;
    }

    public void setOffered(OfferedService offered) {
        this.offered = offered;
    }

    public BigDecimal getPriceAtBooking() {
        return priceAtBooking;
    }

    public void setPriceAtBooking(BigDecimal priceAtBooking) {
        this.priceAtBooking = priceAtBooking;
    }

    public BigDecimal getCommissionPercentage() {
        return commissionPercentage;
    }

    public void setCommissionPercentage(BigDecimal commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }

    public boolean isHomeService() {
        return isHomeService;
    }

    public void setHomeService(boolean homeService) {
        isHomeService = homeService;
    }

}
