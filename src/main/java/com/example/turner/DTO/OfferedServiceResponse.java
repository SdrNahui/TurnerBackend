package com.example.turner.DTO;

import com.example.turner.Model.OfferedService;

import java.math.BigDecimal;

public class OfferedServiceResponse {
    private Long id;
    private String serviceName;
    private BigDecimal price;
    private Integer estimatedDuration;

    public OfferedServiceResponse(OfferedService offeredService){
        this.id = offeredService.getId();
        this.serviceName = offeredService.getServiceName();
        this.price = offeredService.getPrice();
        this.estimatedDuration = offeredService.getEstimatedDuration();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(Integer estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }
}
