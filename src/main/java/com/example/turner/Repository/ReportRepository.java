package com.example.turner.Repository;

import com.example.turner.Model.Report;
import com.example.turner.Service.ReportService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository <Report, Long> {
}
