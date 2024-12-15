package com.picnee.travel.domain.report.repository;

import com.picnee.travel.domain.report.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportRepositoryCustom {

    Page<Report> findReports(Pageable pageable);

}
