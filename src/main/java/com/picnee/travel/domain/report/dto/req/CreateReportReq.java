package com.picnee.travel.domain.report.dto.req;

import com.picnee.travel.domain.report.entity.Report;
import com.picnee.travel.domain.report.entity.ReportTargetType;
import com.picnee.travel.domain.report.entity.ReportType;
import com.picnee.travel.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class CreateReportReq {

    @NotNull(message = "targetId는 필수입니다.")
    private UUID targetId;

    @NotNull
    private ReportTargetType reportTargetType;

    @NotNull
    private ReportType reportType;

    @NotNull
    private Boolean isVisible;

    public Report toEntity(CreateReportReq dto, User user) {
        return Report.builder()
                .targetId(dto.getTargetId())
                .reportTargetType(dto.getReportTargetType())
                .reportType(dto.getReportType())
                .isVisible(getIsVisible())
                .user(user)
                .build();
    }
}