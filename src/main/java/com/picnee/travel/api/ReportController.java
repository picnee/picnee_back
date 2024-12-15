package com.picnee.travel.api;

import com.picnee.travel.domain.report.dto.req.CreateReportReq;
import com.picnee.travel.domain.report.dto.res.FindReportRes;
import com.picnee.travel.domain.report.service.ReportService;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.global.security.annotation.AuthenticatedUser;
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
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<String> createReport(@RequestBody CreateReportReq dto,
                                               @AuthenticatedUser AuthenticatedUserReq auth) {

        return ResponseEntity.status(CREATED).body(reportService.create(dto, auth).getId().toString());
    }

    @DeleteMapping("{reportId}")
    public ResponseEntity<String> deleteReport(@PathVariable("reportId") UUID reportId,
                                               @AuthenticatedUser AuthenticatedUserReq auth) {
        reportService.delete(reportId, auth);
        return ResponseEntity.status(OK).build();
    }

    @GetMapping("{reportId}")
    public ResponseEntity<FindReportRes> findReport(@PathVariable("reportId") UUID reportId,
                                             @AuthenticatedUser AuthenticatedUserReq auth) {
        FindReportRes findReportRes = reportService.find(reportId, auth);
        return ResponseEntity.status(OK).body(findReportRes);
    }

    @GetMapping
    public ResponseEntity<Page<FindReportRes>> findReports(@AuthenticatedUser AuthenticatedUserReq auth,
                                                           @RequestParam(name = "page", defaultValue = "0") int page) {
        Page<FindReportRes> reports = reportService.findReports(auth, page);
        return ResponseEntity.ok().body(reports);
    }



}
