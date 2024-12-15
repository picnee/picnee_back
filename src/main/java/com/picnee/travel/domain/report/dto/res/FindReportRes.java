package com.picnee.travel.domain.report.dto.res;

import com.picnee.travel.domain.report.entity.Report;
import com.picnee.travel.domain.report.entity.ReportTargetType;
import com.picnee.travel.domain.report.entity.ReportType;
import com.picnee.travel.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class FindReportRes {

    private UUID reportId;
    private UUID targetId;
    private ReportTargetType reportTargetType;
    private ReportType reportType;
    private Boolean isVisible;
    private User user;

    public static FindReportRes from(Report report){
        return FindReportRes.builder()
                .reportId(report.getId())
                .targetId(report.getTargetId())
                .reportTargetType(report.getReportTargetType())
                .reportType(report.getReportType())
                .isVisible(report.getIsVisible())
                .user(report.getUser())
                .build();
    }

    public static Page<FindReportRes> paging(Page<Report> reports) {
            List<FindReportRes> reportResList = reports.stream()
                    .map(FindReportRes::from)
                    .toList();

            return new PageImpl<>(reportResList, reports.getPageable(), reports.getTotalElements());
    }
}
