package com.example.turner.Service;

import com.example.turner.Model.Report;
import com.example.turner.Repository.ReportRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }

}
