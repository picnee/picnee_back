package com.picnee.travel.domain.report.repository;

import com.picnee.travel.domain.report.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReportRepositoryCustom {

    Page<Report> findReports(String targetId, String reportTargetType, String reportType, String isVisible, String sort, Pageable pageable);

    Report processReport(UUID reportTargetId);
}
