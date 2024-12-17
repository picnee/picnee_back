package com.picnee.travel.domain.report.repository;

import com.picnee.travel.domain.report.entity.QReport;
import com.picnee.travel.domain.report.entity.Report;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Report> findReports(Pageable pageable) {
        QReport report = QReport.report;

        JPAQuery<Report> query = jpaQueryFactory
                .selectFrom(report);

        List<Report> reports = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = Optional.ofNullable(jpaQueryFactory
                        .select(report.count())
                        .from(report).fetchOne())
                .orElse(0L);

        return new PageImpl<>(reports, pageable, total);
    }
}
