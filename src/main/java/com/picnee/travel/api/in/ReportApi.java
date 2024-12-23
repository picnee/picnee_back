package com.picnee.travel.api.in;

import com.picnee.travel.domain.report.dto.req.CreateReportReq;
import com.picnee.travel.domain.report.dto.res.FindReportRes;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "reports", description = "report API")
public interface ReportApi {
    @Operation(summary = "신고", description = "신고를 진행한다")
    ResponseEntity<String> createReport(CreateReportReq dto, AuthenticatedUserReq auth);

    @Operation(summary = "신고 삭제(어드민)", description = "신고된 것을 삭제한다")
    public ResponseEntity<String> deleteReport(UUID reportId, AuthenticatedUserReq auth);

    @Operation(summary = "신고 단건 조회(어드민)", description = "신고한 내역 단건 조회")
    public ResponseEntity<FindReportRes> findReport(UUID reportId, AuthenticatedUserReq auth);

    @Operation(summary = "신고 다중 건 조회(어드민)", description = "신고한 내역 리스트 조회")
    public ResponseEntity<Page<FindReportRes>> findReports(String targetId, String reportTargetType, String reportType, String isVisible, String sort, int page, AuthenticatedUserReq auth);

    @Operation(summary = "신고 제재(어드민)", description = "신고 제재처리")
    public ResponseEntity<String> processReport(UUID reportTargetId, AuthenticatedUserReq auth);


}
