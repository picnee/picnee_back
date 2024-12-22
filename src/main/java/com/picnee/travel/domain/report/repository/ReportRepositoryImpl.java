package com.picnee.travel.domain.report.repository;

import com.picnee.travel.domain.report.entity.QReport;
import com.picnee.travel.domain.report.entity.Report;
import com.picnee.travel.domain.report.entity.ReportTargetType;
import com.picnee.travel.domain.report.entity.ReportType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;

import org.hibernate.tool.schema.TargetType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Report> findReports(String targetId, String reportTargetType, String reportType, String isVisible, String sort, Pageable pageable) {
        QReport report = QReport.report;

        // 조건 빌딩 메서드 호출
        BooleanBuilder builder = buildCondition(report, targetId, reportTargetType, reportType, isVisible);

        JPAQuery<Report> query = jpaQueryFactory
                .selectFrom(report)
                .where(builder);

        List<Report> reports = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = Optional.ofNullable(jpaQueryFactory
                        .select(report.count())
                        .from(report).fetchOne())
                .orElse(0L);

        return new PageImpl<>(reports, pageable, total);
    }

    @Override
    public Report processReport(UUID reportTargetId) {
        QReport report = QReport.report;

        // 신고하기 위한 targetId가 같은 reportId 목록을 선 조회
        List<UUID> reportIds = jpaQueryFactory
                .select(report.id)
                .from(report)
                .where(report.targetId.eq(reportTargetId))
                .fetch();

        // isVisible 업데이트 처리
        if (!reportIds.isEmpty()) {
            jpaQueryFactory
                    .update(report)
                    .set(report.isVisible, true)
                    .where(report.id.in(reportIds))
                    .execute();
        }

        return null;
    }

    // 조건 빌딩
    private BooleanBuilder buildCondition(QReport report, String targetId, String reportTargetType, String reportType, String isVisible) {
        BooleanBuilder builder = new BooleanBuilder();

        // 기본 조건 추가
        if ("true".equalsIgnoreCase(isVisible)) {
            builder.and(report.isVisible.isTrue());
        } else {
            builder.and(report.isVisible.isFalse());
        }

        // 조건별 동적 빌딩
        if (targetId != null) {
            builder.and(report.targetId.eq(UUID.fromString(targetId)));
        }
        if (reportTargetType != null) {
            try {
                builder.and(report.reportTargetType.eq(ReportTargetType.valueOf(reportTargetType)));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid reportTargetType: " + reportTargetType);
            }
        }
        if (reportType != null) {
            builder.and(report.reportType.eq(ReportType.valueOf(reportType)));
        }

        return builder;
    }
}
