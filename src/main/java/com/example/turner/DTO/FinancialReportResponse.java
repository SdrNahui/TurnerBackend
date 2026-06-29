package com.example.turner.DTO;

import com.example.turner.Model.FinancialReport;

import java.math.BigDecimal;

public class FinancialReportResponse {
    private BigDecimal totalGross;
    private BigDecimal totalCommission;
    private BigDecimal totalNet;
    private int totalAppointment;
    public FinancialReportResponse(FinancialReport financialReport){
        totalGross = financialReport.getTotalGross();
        totalCommission = financialReport.getTotalCommission();
        totalNet = financialReport.getTotalNet();
        totalAppointment = financialReport.getTotalAppointment();
    }

    public BigDecimal getTotalGross() {
        return totalGross;
    }

    public void setTotalGross(BigDecimal totalGross) {
        this.totalGross = totalGross;
    }

    public BigDecimal getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(BigDecimal totalCommission) {
        this.totalCommission = totalCommission;
    }

    public BigDecimal getTotalNet() {
        return totalNet;
    }

    public void setTotalNet(BigDecimal totalNet) {
        this.totalNet = totalNet;
    }

    public int getTotalAppointment() {
        return totalAppointment;
    }

    public void setTotalAppointment(int totalAppointment) {
        this.totalAppointment = totalAppointment;
    }
}
