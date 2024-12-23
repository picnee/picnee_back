package com.picnee.travel.api;

import com.picnee.travel.api.in.ReportApi;
import com.picnee.travel.domain.report.dto.req.CreateReportReq;
import com.picnee.travel.domain.report.dto.res.FindReportRes;
import com.picnee.travel.domain.report.service.ReportService;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.global.security.annotation.AuthenticatedUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController implements ReportApi {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<String> createReport(@Valid @RequestBody CreateReportReq dto,
                                               @AuthenticatedUser AuthenticatedUserReq auth) {

        return ResponseEntity.status(CREATED).body(reportService.create(dto, auth).getId().toString());
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<String> deleteReport(@PathVariable("reportId") UUID reportId,
                                               @AuthenticatedUser AuthenticatedUserReq auth) {
        reportService.delete(reportId, auth);
        return ResponseEntity.status(OK).build();
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<FindReportRes> findReport(@PathVariable("reportId") UUID reportId,
                                             @AuthenticatedUser AuthenticatedUserReq auth) {
        FindReportRes findReportRes = reportService.find(reportId, auth);
        return ResponseEntity.status(OK).body(findReportRes);
    }

    @GetMapping
    public ResponseEntity<Page<FindReportRes>> findReports(@RequestParam(name = "targetId", required = false) String targetId,
                                                           @RequestParam(name = "reportTargetType", required = false) String reportTargetType,
                                                           @RequestParam(name = "reportType", required = false) String reportType,
                                                           @RequestParam(name = "is_visible", required = false) String isVisible,
                                                           @RequestParam(name = "sort", required = false) String sort,
                                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                                            @AuthenticatedUser AuthenticatedUserReq auth) {
        Page<FindReportRes> reports = reportService.findReports(auth, targetId, reportTargetType, reportType, isVisible, sort, page);
        return ResponseEntity.status(OK).body(reports);
    }

    @PatchMapping("/{reportId}")
    public ResponseEntity<String> processReport(@PathVariable("reportId") UUID reportTargetId,
                                                @AuthenticatedUser AuthenticatedUserReq auth) {
        reportService.processReport(reportTargetId, auth);
        return ResponseEntity.status(OK).build();
    }



}
